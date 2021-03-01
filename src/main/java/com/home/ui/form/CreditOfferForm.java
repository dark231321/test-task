package com.home.ui.form;

import com.home.backend.model.CreditOffer;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.w3c.dom.Text;

import java.text.NumberFormat;

public class CreditOfferForm extends Window implements View {


    private final TextField lastName        = new TextField("Last name");
    private final TextField firstName       = new TextField("First name");
    private final TextField middleName      = new TextField("Middle name");
    private final TextField phoneNumber     = new TextField("Phone number");
    private final TextField email           = new TextField("Email");
    private final TextField passport        = new TextField("Passport");

    private final TextField creditSum       = new TextField("Credit sum");
    private final TextField mouthDuration   = new TextField("Mouth duration");

    private final Button ok                 = new Button("OK");
    private final static NumberFormat f     = NumberFormat.getInstance();;
    private final CreditOffer creditOffer;

    public CreditOfferForm(CreditOffer creditOffer){
        this.creditOffer = creditOffer;
        setCaption(" Inputs data");
        setIcon(VaadinIcons.USER);
        setModal(true);
        center();
        setContent(drawForm());
        ok.addClickListener(clickEvent -> getUI().removeWindow(CreditOfferForm.this));
    }

    private Component drawForm() {
        VerticalLayout verticalLayout
                = new VerticalLayout(firstName, middleName, lastName, phoneNumber, email, passport, creditSum, mouthDuration);
        lastName.setEnabled(false);
        firstName.setEnabled(false);
        middleName.setEnabled(false);
        phoneNumber.setEnabled(false);
        email.setEnabled(false);
        passport.setEnabled(false);
        creditSum.setEnabled(false);
        mouthDuration.setEnabled(false);

        firstName       .setValue(creditOffer.getClient().getFirstName());
        middleName      .setValue(creditOffer.getClient().getMiddleName());
        lastName        .setValue(creditOffer.getClient().getLastName());
        phoneNumber     .setValue(creditOffer.getClient().getPhoneNumber());
        email           .setValue(creditOffer.getClient().getEmail());
        passport        .setValue(creditOffer.getClient().getPassport());
        creditSum       .setValue(f.format(creditOffer.getCreditSum()));
        mouthDuration   .setValue(f.format(12 * creditOffer.getCreditPeriod()));
        verticalLayout.addComponent(ok);
        return verticalLayout;
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
