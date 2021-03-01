package com.home.ui.view;

import com.home.backend.model.Bank;
import com.home.backend.model.Client;
import com.home.backend.model.Credit;
import com.home.backend.model.CreditOffer;
import com.home.backend.service.impl.*;
import com.home.ui.form.CreditOfferForm;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@SpringView(name = "credit-offer")
@Slf4j
public class CreditOfferView extends VerticalLayout implements View {

    private final ClientServiceImpl clientService;
    private final CreditServiceImpl creditService;
    private final BankServiceImpl bankService;
    private final CreditOfferServiceImpl creditOfferService;
    private final PaymentService paymentService;

    private NativeSelect<Credit> creditNativeSelect;
    private NativeSelect<Client> clientNativeSelect;

    private final TextField creditDuration  = new TextField("Credit duration");
    private final TextField creditAmount    = new TextField("Credit amount");

    private final Button save               = new Button("SAVE");

    @Autowired
    public CreditOfferView(ClientServiceImpl clientService,
                           CreditServiceImpl creditService,
                           BankServiceImpl bankService,
                           CreditOfferServiceImpl creditOfferService,
                           PaymentService paymentService) {
        this.paymentService = paymentService;
        this.clientService = clientService;
        this.creditService = creditService;
        this.bankService = bankService;
        this.creditOfferService = creditOfferService;

        drawForm();
    }

    private void drawForm() {
        creditNativeSelect = new NativeSelect<>("Credit", creditService.findAll());
        clientNativeSelect = new NativeSelect<>("Client", clientService.findAll());
        setCaption(" Inputs data");
        setIcon(VaadinIcons.USER);
        creditNativeSelect = new NativeSelect<>("Credits", creditService.findAll());
        clientNativeSelect = new NativeSelect<>("Clients", clientService.findAll());
        VerticalLayout formInputsFields
                = new VerticalLayout(creditNativeSelect, clientNativeSelect);
        HorizontalLayout inputsFields = new HorizontalLayout(creditAmount, creditDuration);
        HorizontalLayout formButtons = new HorizontalLayout(save);
        addComponents(formInputsFields, inputsFields, formButtons);
        buttonsListener();
    }

    private void buttonsListener(){
        save.addClickListener(clickEvent -> saveCredit());
    }

    private void saveCredit() {
        Credit credit = creditNativeSelect.getValue();
        Client client = clientNativeSelect.getValue();
        if(validateDate()){
            try {
                Bank bank = bankService.save(Bank.builder()
                        .clients(client)
                        .credits(credit)
                        .build()).orElseThrow(IllegalArgumentException::new);
                CreditOffer creditOffer =
                        creditOfferService.save(CreditOffer.builder()
                                .credit(credit)
                                .client(client)
                                .payments(new ArrayList<>())
                                .creditSum(Double.parseDouble(creditAmount.getValue()))
                                .creditPeriod(Integer.parseInt(creditDuration.getValue()))
                                .bank(bank)
                                .build());
                getUI().addWindow(new CreditOfferForm(creditOffer));
            }catch (Exception e){
                log.info("EXC", e);
                new Notification("Error! Check inputs data",
                        Notification.Type.ERROR_MESSAGE).show(getUI().getPage());
            }
        } else {
            new Notification("Error! Check inputs data",
                    Notification.Type.ERROR_MESSAGE).show(getUI().getPage());
        }
    }

    private boolean validateDate(){
        if(creditNativeSelect.getValue() == null || clientNativeSelect.getValue() == null ||
                creditDuration.getValue() == null || creditAmount.getValue() == null)
            return false;

        double inputCreditAmount = Double.parseDouble(creditAmount.getValue());
        int inputCreditDuration  = Integer.parseInt(creditDuration.getValue());
        return creditNativeSelect.getValue().getCreditLimit() >= Double.parseDouble(creditAmount.getValue())
                && inputCreditDuration > 0 && inputCreditAmount > 0;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
