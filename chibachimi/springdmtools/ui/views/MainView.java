package com.chibachimi.springdmtools.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

@Route("")
public class MainView extends VerticalLayout {

    @Autowired
    private ApplicationContext context;

    // IntelliJ says this isn't used, but it certainly is!
    public MainView() {
        var f1 = new Button( "Help",
                e -> UI.getCurrentOrThrow().navigate(HelpView.class)
        );
        var f2 = new Button( "Games",
                e -> UI.getCurrentOrThrow().navigate(GameView.class)
        );
        var f3 = new Button( "Initiative Manager",
                e -> UI.getCurrentOrThrow().navigate(InitiativeView.class)
        );

        // TODO Rename this?
        var closeBtn = new Button("Close Process");
        closeBtn.addClickListener(e -> {
            System.out.println("Exiting");
            SpringApplication.exit(context);
        });

        setAlignItems(Alignment.CENTER);

        add(f1, f2, f3, closeBtn);
    }

}
