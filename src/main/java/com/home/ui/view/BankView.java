package com.home.ui.view;

import com.home.backend.model.Bank;
import com.home.backend.service.impl.BankServiceImpl;
import com.home.backend.service.impl.ClientServiceImpl;
import com.home.backend.service.impl.CreditOfferServiceImpl;
import com.home.backend.service.impl.CreditServiceImpl;
import com.home.ui.form.BankForm;
import com.home.ui.form.CreditDetails;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = "bank")
@Slf4j
public class BankView extends VerticalLayout implements View {

    private final Button creditDetails = new Button("CREDIT DETAILS", VaadinIcons.CHECK);
    private final BankServiceImpl bankService;
    private final CreditOfferServiceImpl creditOfferService;

    public static Grid<Bank> bankGrid = new Grid<>(Bank.class);

    @Autowired
    public BankView(CreditOfferServiceImpl creditOfferService,
                    BankServiceImpl bankService){
        this.creditOfferService = creditOfferService;
        this.bankService = bankService;
        Page.getCurrent().setTitle("bank");
        creditDetails.setIcon(VaadinIcons.PLUS);
        bankGrid.setSizeFull();
        bankGrid.setColumns("credits", "clients");
        creditDetails.setEnabled(false);

        bankGrid.setItems(bankService.findAll());

        addComponent(bankGrid);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(creditDetails);
        horizontalLayout.setComponentAlignment(creditDetails, Alignment.TOP_RIGHT);
        horizontalLayout.setSizeFull();
        addComponent(horizontalLayout);
        addButtonsListener();
    }

    private void addButtonsListener() {
        bankGrid.addSelectionListener(valueChangeEvent -> creditDetails.setEnabled(!bankGrid.asSingleSelect().isEmpty()));

        creditDetails.addClickListener(
                clickEvent -> {
                    try {
                        getUI().addWindow(new CreditDetails(creditOfferService,
                                bankGrid.asSingleSelect().getValue()));
                    }
                    catch (Exception ex){
                        log.info("EXC", ex);
                    }
                }
        );
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }

}
