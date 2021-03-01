package com.home.ui.view;

import com.home.backend.model.Client;
import com.home.backend.service.impl.ClientServiceImpl;

import com.home.ui.form.ClientForm;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;


@SpringView(name = "clients")
public class ClientView extends VerticalLayout implements View {


    private final ClientServiceImpl clientService;

    public static Grid<Client> clientGrid = new Grid<>(Client.class);
    private final Button addButton = new Button("ADD");
    private final Button editButton = new Button("EDIT");
    private final Button deleteButton = new Button("DELETE");

    @Autowired
    public ClientView(ClientServiceImpl clientService){
        this.clientService = clientService;
        Page.getCurrent().setTitle("Clients");
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);
        deleteButton.setStyleName(ValoTheme.BUTTON_DANGER);
        editButton.setIcon(VaadinIcons.PENCIL);
        deleteButton.setIcon(VaadinIcons.MINUS);
        addButton.setIcon(VaadinIcons.PLUS);
        clientGrid.setSizeFull();
        clientGrid.setColumns("lastName" , "firstName" , "middleName", "phoneNumber", "email", "passport");
        clientGrid.setItems(clientService.findAll());
        addComponent(clientGrid);
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(addButton, editButton, deleteButton);
        horizontalLayout.setSizeFull();
        horizontalLayout.setComponentAlignment(editButton, Alignment.TOP_CENTER);
        horizontalLayout.setComponentAlignment(deleteButton, Alignment.TOP_RIGHT);
        addComponent(horizontalLayout);
        addButtonsListener();
    }

    private void addButtonsListener() {

        clientGrid.addSelectionListener(valueChangeEvent ->
        {
            if (!clientGrid.asSingleSelect().isEmpty()) {
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
            } else {
                editButton.setEnabled(false);
                deleteButton.setEnabled(false);
            }
        });

        deleteButton.addClickListener(
                clickEvent -> {
                    clientService.delete(clientGrid.asSingleSelect().getValue().getId());
                    updateGrid(clientService);
                }
        );

        addButton.addClickListener(
                clickEvent ->
                        getUI().addWindow(new ClientForm(clientService, null))
                );

        editButton.addClickListener(
                clickEvent -> getUI().addWindow(new ClientForm(clientService, clientGrid.asSingleSelect().getValue()))
        );
    }

    public static void updateGrid(ClientServiceImpl clientService){
        clientGrid.setItems(clientService.findAll());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
