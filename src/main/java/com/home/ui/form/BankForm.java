package com.home.ui.form;

import com.home.backend.model.Bank;
import com.home.backend.model.Client;
import com.home.backend.model.Credit;
import com.home.backend.model.Payment;
import com.home.backend.service.impl.BankServiceImpl;
import com.home.backend.service.impl.ClientServiceImpl;
import com.home.backend.service.impl.CreditOfferServiceImpl;
import com.home.backend.service.impl.CreditServiceImpl;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

public class BankForm extends Window implements View {

    private final ClientServiceImpl clientService;
    private final CreditServiceImpl creditService;
    private final BankServiceImpl bankService;
    private final CreditOfferServiceImpl creditOfferService;


    private NativeSelect<Credit> creditNativeSelect;
    private NativeSelect<Client> clientNativeSelect;

    private final TextField creditDuration  = new TextField("Credit duration");
    private final TextField creditAmount    = new TextField("Credit amount");

    private final Button save               = new Button("SAVE");
    private final Button cancel             = new Button("CANCEL");


    public BankForm(CreditServiceImpl creditService,
                    ClientServiceImpl clientService,
                    BankServiceImpl bankService,
                    CreditOfferServiceImpl creditOfferService) {
        this.clientService = clientService;
        this.creditService = creditService;
        this.bankService = bankService;
        this.creditOfferService = creditOfferService;
        setCaption(" Inputs data");
        setIcon(VaadinIcons.USER);
        setModal(true);
        center();
        setContent(drawForm());
    }

    public Component drawForm(){
        VerticalLayout verticalLayout = new VerticalLayout();
        creditNativeSelect = new NativeSelect<>("Credits", creditService.findAll());
        clientNativeSelect = new NativeSelect<>("Clients", clientService.findAll());
        VerticalLayout formInputsFields
                = new VerticalLayout(creditNativeSelect, clientNativeSelect);
        HorizontalLayout inputsFields = new HorizontalLayout(creditAmount, creditDuration);
        HorizontalLayout formButtons = new HorizontalLayout(save, cancel);
        verticalLayout.addComponents(formInputsFields, inputsFields, formButtons);
        cancel.addClickListener(clickEvent -> getUI().removeWindow(BankForm.this));
        save.addClickListener(clickEvent -> save());
        return verticalLayout;
    }

    private void save() {
        Credit credit = creditNativeSelect.getValue();
        Client client = clientNativeSelect.getValue();
        if(credit != null && client != null && creditDuration.getValue() != null && creditAmount.getValue() != null) {
            bankService.save(Bank.builder()
                    .clients(client)
                    .credits(credit)
                    .build());
            CreditOfferServiceImpl creditOfferService;
        } else {
            new Notification("Error! Check inputs data",
                    Notification.Type.ERROR_MESSAGE).show(getUI().getPage());
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

}
