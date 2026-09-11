package com.chibachimi.springdmtools.experimental;

import com.chibachimi.springdmtools.ui.components.DiceMenu;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("/dice")
public class DiceView extends VerticalLayout {

    private final DiceMenu diceMenu;

    @Autowired
    public DiceView(DiceMenu dm) {
        this.diceMenu = dm;

        HorizontalLayout layout = new HorizontalLayout(this.diceMenu);

        this.diceMenu.show();
        setAlignItems(Alignment.CENTER);

        add(layout);
    }
//
//    public DiceView() {
//        DiceLogic logic = new DiceLogic();
//
//        TextField f1 = new TextField("Number");
//        TextField f2 = new TextField("Face");
//        TextField  f3 = new TextField("Bonus");
//        TextField result = new TextField("Result");
//
//        Button btn = new Button("Roll", e -> {
//            var roll = logic.rollDice(f1.getValue(), f2.getValue(), f3.getValue());
//            result.setValue(String.valueOf(roll));
//        });
//
//        add(f1, f2, f3, btn, result);
//    }
}