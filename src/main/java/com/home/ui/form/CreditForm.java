package com.home.ui.form;

import com.home.backend.model.Credit;
import com.home.backend.service.impl.CreditServiceImpl;
import com.home.ui.view.CreditView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreditForm extends Window implements View {

    private final TextField creditLimit     = new TextField("creditLimit");
    private final TextField interestRate    = new TextField("interestRate");

    private final Button save               = new Button("SAVE");
    private final Button cancel             = new Button("CANCEL");

    private final CreditServiceImpl creditService;
    private final Credit credit;


    public CreditForm(CreditServiceImpl creditService, Credit credit){
        this.creditService = creditService;
        this.credit = credit;
        setCaption(" Заполните данные");
        setIcon(VaadinIcons.USER);
        setModal(true);
        center();
        setContent(drawForm());
    }

    private Component drawForm() {
        VerticalLayout verticalLayout = new VerticalLayout();
        VerticalLayout formInputsFields
                = new VerticalLayout(creditLimit, interestRate);

        if(credit != null){
            creditLimit    .setValue(Double.toString(credit.getCreditLimit()));
            interestRate   .setValue(Double.toString(credit.getInterestRate()));
        }
        HorizontalLayout formButtons = new HorizontalLayout(save, cancel);
        verticalLayout.addComponents(formInputsFields, formButtons);
        save.addClickListener(clickEvent -> save());
        cancel.addClickListener(clickEvent -> getUI().removeWindow(CreditForm.this));
        return verticalLayout;
    }

    private void save() {
        if(validateDate()) {
            if (credit != null) {
                creditService.update(Credit.builder()
                        .id(credit.getId())
                        .creditLimit(Double.parseDouble(creditLimit.getValue().trim()))
                        .interestRate(Double.parseDouble(interestRate.getValue().trim()))
                        .build());
            } else {
                creditService.save(Credit.builder()
                        .creditLimit(Double.parseDouble(creditLimit.getValue().trim()))
                        .interestRate(Double.parseDouble(interestRate.getValue().trim()))
                        .build());
            }
            Notification success = new Notification("Операция завершена успешно!",
                    Notification.Type.HUMANIZED_MESSAGE);
            success.setDelayMsec(2000);
            success.show(getUI().getPage());
            getUI().removeWindow(CreditForm.this);
            CreditView.updateGrid(creditService);
        } else {
            new Notification("Error! Check inputs data",
                    Notification.Type.ERROR_MESSAGE).show(getUI().getPage());
        }
    }

    private boolean validateDate(){
        if(creditLimit.getValue() != null && interestRate.getValue() != null){
            try {
                double creditLimitInput = Double.parseDouble(creditLimit.getValue().trim());
                double interestRateInput = Double.parseDouble(interestRate.getValue().trim());
                return creditLimitInput > 0.0  &&
                        interestRateInput > 0.0 && interestRateInput <= 20.0 ;
            } catch (NumberFormatException e){
                return false;
            }
        }
        return false;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}
