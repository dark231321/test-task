package com.home.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.io.Serializable;

@Theme("valo")
@SpringUI
@SpringViewDisplay
public class MyUi extends UI implements ViewDisplay, Serializable {

    final VerticalLayout root = new VerticalLayout();
    public static HorizontalLayout buttonsLayout;
    private Panel springViewDisplay;

    @Override
    protected void init(VaadinRequest request) {

        root.setSizeFull();
        setContent(root);

        final HorizontalLayout navigationBar = new HorizontalLayout();
        buttonsLayout = new HorizontalLayout();

        navigationBar.setWidth("100%");

        buttonsLayout.addComponents(
                createNavigationButton("Main", ""),
                createNavigationButton("Banks", "bank"),
                createNavigationButton("Clients", "clients"),
                createNavigationButton("Credits", "credits"),
                createNavigationButton("Create new order", "credit-offer")
        );
        for(int i=1; i<buttonsLayout.getComponentCount(); i++)
            buttonsLayout.getComponent(i).addStyleName(ValoTheme.BUTTON_QUIET);

        buttonsLayout.getComponent(0).addStyleName(ValoTheme.BUTTON_PRIMARY);

        navigationBar.addComponents(buttonsLayout);


        springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();

        root.addComponents(navigationBar);
        root.addComponent(springViewDisplay);
        root.setExpandRatio(springViewDisplay, 1.0f);
    }

    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }

    @Override
    public void showView(View view) {
        springViewDisplay.setContent((Component) view);
    }
}
