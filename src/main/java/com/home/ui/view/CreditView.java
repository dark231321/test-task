package com.home.ui.view;

import com.home.backend.model.Credit;
import com.home.backend.service.impl.CreditServiceImpl;
import com.home.ui.form.CreditForm;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = "credits")
public class CreditView extends VerticalLayout implements View {
    private CreditServiceImpl creditService;


    private static Grid<Credit> creditGrid = new Grid<>(Credit.class);
    private final Button addButton = new Button("ADD");
    private final Button editButton = new Button("EDIT");
    private final Button deleteButton = new Button("DELETE");

    @Autowired
    public CreditView(CreditServiceImpl creditService){
        this.creditService = creditService;
        Page.getCurrent().setTitle("Credit");
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);
        editButton.setIcon(VaadinIcons.PENCIL);
        deleteButton.setIcon(VaadinIcons.MINUS);
        addButton.setIcon(VaadinIcons.PLUS);
        creditGrid.setSizeFull();
        creditGrid.setItems(creditService.findAll());
        creditGrid.setColumns();
        creditGrid.addColumn(Credit::getFormattedCreditLimit).setCaption("Credit Limit");
        creditGrid.addColumn(Credit::getFormattedInterestRate).setCaption("Interest Rate");

        addComponent(creditGrid);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(addButton, editButton, deleteButton);
        horizontalLayout.setSizeFull();
        horizontalLayout.setComponentAlignment(editButton, Alignment.TOP_CENTER);
        horizontalLayout.setComponentAlignment(deleteButton, Alignment.TOP_RIGHT);
        addComponent(horizontalLayout);
        addButtonsListener();
    }

    private void addButtonsListener() {
        creditGrid.addSelectionListener(valueChangeEvent ->
        {
            if (!creditGrid.asSingleSelect().isEmpty()) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
            } else {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        });
        deleteButton.addClickListener(
                clickEvent -> {
                    creditService.delete(creditGrid.asSingleSelect().getValue().getId());
                    updateGrid(creditService);
                }
        );
        addButton.addClickListener(
                clickEvent ->
                        getUI().addWindow(new CreditForm(creditService, null))
        );
        editButton.addClickListener(
                clickEvent -> getUI().addWindow(new CreditForm(creditService, creditGrid.asSingleSelect().getValue()))
        );
    }

    public static void updateGrid(CreditServiceImpl creditService){
        creditGrid.setItems(creditService.findAll());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
