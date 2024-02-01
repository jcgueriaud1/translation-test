package com.example.application.views.helloworld;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;

import java.util.Locale;

@PageTitle("Hello World")
@Route(value = "hello", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class HelloWorldView extends VerticalLayout implements AfterNavigationObserver {

    private TextField name;
    private Button changeLanguageToFrench;
    private Button changeLanguageToFinnish;

    public HelloWorldView() {
        name = new TextField(getTranslation("name"));
        changeLanguageToFrench = new Button("Change language to French");
        changeLanguageToFrench.addClickListener(e -> {
            VaadinSession.getCurrent().setLocale(Locale.FRENCH);
            getUI().get().getPage().reload();
        });

        changeLanguageToFinnish = new Button("Change language to Finnish");
        changeLanguageToFinnish.addClickListener(e -> {
            VaadinSession.getCurrent().setLocale(new Locale("fi", "FI"));
            getUI().get().getPage().reload();
        });
        setMargin(true);

        add(name, changeLanguageToFrench, changeLanguageToFinnish);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        add(new Span("Default locale:" + Locale.getDefault()));
        add(new Span("UI locale:" + getUI().get().getLocale()));
        add(new Span("Vaadin session locale:" + VaadinSession.getCurrent().getLocale()));

        getElement().executeJs("return document.documentElement.getAttribute(\"lang\")").then(String.class,
                result -> {
                    add(new Span("HTML LANG TAG locale:" + result));

                }
        );
    }
}
