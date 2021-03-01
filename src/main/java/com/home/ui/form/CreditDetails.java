package com.home.ui.form;

import com.home.backend.dto.PaymentDto;
import com.home.backend.model.*;
import com.home.backend.service.impl.CreditOfferServiceImpl;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreditDetails extends Window implements View {

    private final Grid<PaymentDto> paymentGrid = new Grid<>(PaymentDto.class);
    private final Button ok = new Button("OK");
    private final CreditOfferServiceImpl creditOfferService;
    private final Bank bank;

    public CreditDetails(CreditOfferServiceImpl creditOfferService, Bank bank){
        this.creditOfferService = creditOfferService;
        this.bank = bank;
        setIcon(VaadinIcons.USER);
        setModal(true);
        center();
        CreditDetails.this.setSizeFull();
        setContent(drawForm());
        ok.addClickListener(clickEvent -> getUI().removeWindow(CreditDetails.this));
    }

    private Component drawForm() {
        VerticalLayout verticalLayout = new VerticalLayout();
        try {
            paymentGrid.setColumns("paymentDate","paymentAmount", "paymentBody", "interestRepayment");
            paymentGrid.setItems(creditOfferService.findPaymentsByBank(bank));
            verticalLayout.setSizeFull();
            paymentGrid.setSizeFull();
            verticalLayout.addComponents(paymentGrid, ok);

        } catch (Exception ex){
            log.info("EXC", ex);
        }

        return verticalLayout;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
