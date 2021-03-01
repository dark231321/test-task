package com.home.ui.view;

import javax.annotation.PostConstruct;

import com.home.ui.MyUi;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;


import java.io.File;
import java.io.Serializable;

@SpringView(name = "")
public class MainView extends VerticalLayout implements View {
    @PostConstruct
    void init() {
        MyUi.buttonsLayout.setVisible(false);

        HorizontalLayout mainLayout = new HorizontalLayout();
        VerticalLayout bankLayout = new VerticalLayout();
        VerticalLayout creditLayout = new VerticalLayout();
        VerticalLayout clientsLayout = new VerticalLayout();
        VerticalLayout offerLayout = new VerticalLayout();

        Label bankLabel = new Label("Bank");
        bankLabel.addStyleName(ValoTheme.LABEL_BOLD);
        bankLabel.addStyleName(ValoTheme.LABEL_COLORED);

        Label creditLabel = new Label("Credits");
        creditLabel.addStyleName(ValoTheme.LABEL_BOLD);
        creditLabel.addStyleName(ValoTheme.LABEL_COLORED);

        Label clientsLabel = new Label("Clients");
        clientsLabel.addStyleName(ValoTheme.LABEL_BOLD);
        clientsLabel.addStyleName(ValoTheme.LABEL_COLORED);

        Label offerLabel = new Label("Create new order");
        offerLabel.addStyleName(ValoTheme.LABEL_BOLD);
        offerLabel.addStyleName(ValoTheme.LABEL_COLORED);

        bankLayout.addComponents(bankLabel);
        bankLayout.setComponentAlignment(bankLabel, Alignment.TOP_CENTER);
        bankLayout.addLayoutClickListener(event -> {
            getUI().getNavigator().navigateTo("bank");
            MyUi.buttonsLayout.setVisible(true);
        });
        bankLayout.addStyleName(ValoTheme.LAYOUT_WELL);

        creditLayout.addComponents(creditLabel);
        creditLayout.setComponentAlignment(creditLabel, Alignment.TOP_CENTER);
        creditLayout.addLayoutClickListener(event -> {
            getUI().getNavigator().navigateTo("credits");
            MyUi.buttonsLayout.setVisible(true);
        });
        creditLayout.addStyleName(ValoTheme.LAYOUT_WELL);

        clientsLayout.addComponents(clientsLabel);
        clientsLayout.setComponentAlignment(clientsLabel, Alignment.TOP_CENTER);
        clientsLayout.addLayoutClickListener(event -> {
            getUI().getNavigator().navigateTo("clients");
            MyUi.buttonsLayout.setVisible(true);
        });
        clientsLayout.addStyleName(ValoTheme.LAYOUT_WELL);

        offerLayout.addComponents(offerLabel);
        offerLayout.setComponentAlignment(offerLabel, Alignment.TOP_CENTER);
        offerLayout.addLayoutClickListener(event -> {
            getUI().getNavigator().navigateTo("credit-offer");
            MyUi.buttonsLayout.setVisible(true);
        });
        offerLayout.addStyleName(ValoTheme.LAYOUT_WELL);

        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        mainLayout.setWidth("100%");
        mainLayout.addComponents(bankLayout, creditLayout, clientsLayout, offerLayout);
        addComponent(mainLayout);
    }

    @Override
    public void enter(ViewChangeEvent viewChangeEvent) {

    }
}
