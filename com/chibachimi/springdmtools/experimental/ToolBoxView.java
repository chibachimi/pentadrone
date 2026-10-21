package com.chibachimi.springdmtools.experimental;

import com.chibachimi.springdmtools.ui.views.DiceView;
import com.chibachimi.springdmtools.ui.views.InitiativeView;
import com.chibachimi.springdmtools.ui.views.TableMasterView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("/toolbox")
public class ToolBoxView extends VerticalLayout {

    @Autowired
    public ToolBoxView() {
        Button btnInit = new Button("Initiative View",
                e ->
                    UI.getCurrentOrThrow().navigate(InitiativeView.class));
        Button btnDice = new Button("Dice",
                e -> UI.getCurrentOrThrow().navigate(DiceView.class));
        Button btnTables = new Button("Tables",
                e -> UI.getCurrentOrThrow().navigate(TableMasterView.class));

        this.add(btnInit, btnDice, btnTables);

        setAlignItems(Alignment.CENTER);
    }
}
