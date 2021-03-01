package com.home.ui.form;


import com.home.backend.model.Client;
import com.home.backend.service.impl.ClientServiceImpl;
import com.home.ui.view.ClientView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

public class ClientForm extends Window implements View {

    private final TextField lastName    = new TextField("lastName");
    private final TextField firstName   = new TextField("firstName");
    private final TextField middleName  = new TextField("middleName");
    private final TextField phoneNumber = new TextField("phoneNumber");
    private final TextField email       = new TextField("email");
    private final TextField passport    = new TextField("passport");

    private final Button save           = new Button("SAVE");
    private final Button cancel         = new Button("CANCEL");

    private final ClientServiceImpl clientService;
    private final Client client;

    public ClientForm(ClientServiceImpl clientService, Client client) {
        this.clientService = clientService;
        this.client = client;
        setCaption(" Заполните данные");
        setIcon(VaadinIcons.USER);
        setModal(true);
        center();
        setContent(drawForm());
    }

    public Component drawForm(){
        VerticalLayout verticalLayout = new VerticalLayout();


        if(client != null){
            lastName    .setValue(client.getLastName());
            firstName   .setValue(client.getFirstName());
            middleName  .setValue(client.getMiddleName());
            passport    .setValue(client.getPassport());
            email       .setValue(client.getEmail());
            phoneNumber .setValue(client.getPhoneNumber());
        }
        VerticalLayout formInputsFields
                = new VerticalLayout(lastName, firstName, middleName, phoneNumber, email, passport);
        HorizontalLayout formButtons = new HorizontalLayout(save, cancel);
        verticalLayout.addComponents(formInputsFields, formButtons);
        save.addClickListener(clickEvent -> save());
        cancel.addClickListener(clickEvent -> getUI().removeWindow(ClientForm.this));
        return verticalLayout;
    }

    private void save() {
        if(validateDate()){
            if(client != null) {
                clientService.update(Client.builder()
                        .id(client.getId())
                        .firstName(firstName.getValue())
                        .middleName(middleName.getValue())
                        .lastName(lastName.getValue())
                        .passport(passport.getValue())
                        .email(email.getValue())
                        .phoneNumber(phoneNumber.getValue())
                        .build());
            } else {
                clientService.save(Client.builder()
                        .firstName(firstName.getValue())
                        .middleName(middleName.getValue())
                        .lastName(lastName.getValue())
                        .passport(passport.getValue())
                        .email(email.getValue())
                        .phoneNumber(phoneNumber.getValue())
                        .build());
            }
            new Notification("Success operation!",
                    Notification.Type.HUMANIZED_MESSAGE).show(getUI().getPage());
            getUI().removeWindow(ClientForm.this);
            ClientView.updateGrid(clientService);
        }else{
            new Notification("Error! Check inputs data",
                    Notification.Type.ERROR_MESSAGE).show(getUI().getPage());
        }
    }

    private boolean validateDate(){
        if(firstName.getValue()         != null &&
                middleName.getValue()   != null &&
                lastName.getValue()     != null &&
                passport.getValue()     != null &&
                email.getValue()        != null &&
                phoneNumber.getValue()  != null){
            return phoneNumber.getValue().matches("^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$");
        }
        return false;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
