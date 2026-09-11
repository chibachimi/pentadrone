package com.chibachimi.springdmtools.ui.components;

import com.chibachimi.springdmtools.experimental.DiceLogic;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// TODO ADD TOOLTIPS
@UIScope
@Component
public class DiceMenu extends VerticalLayout {

    private final DiceLogic logic;

    TextField fieldResults;
    Text textDetails;

    IntegerField numberField;
    Select<Integer> faceSelect;
    IntegerField bonusField;

    @Autowired
    public DiceMenu(DiceLogic dl) {
        this.logic = dl;

        // Could extract each Layout into its own thing, but I think this is small enough it should look fine
        numberField = new IntegerField("Number of dice");
        numberField.setValue(1);
        Div numberDiv = new Div();
        numberDiv.setText("#");
        numberField.setPrefixComponent(numberDiv);

        faceSelect = new Select<>("Faces");
        faceSelect.setPlaceholder("Select Dice");
        faceSelect.setItems(4, 6, 8, 10, 12, 20, 100);

        bonusField = new IntegerField("Bonus");
        bonusField.setValue(0);
        Div bonusDiv = new Div("+");
        bonusField.setPrefixComponent(bonusDiv);

        fieldResults = new TextField("Results");
        textDetails = new Text("");

        Button btnAdv = new Button("Roll Advantage",
                ea->rollAdv());
        Button btnDis = new Button("Roll Disadvantage",
                ed->rollDis());
        Button btnCoin = new Button("Flip Coin",
                event->flip());

        Button btnRoll = new Button("Roll!",
                event1-> calc());
        Button btnClear = new Button("Clear",
                event2-> clear());
        Button btnLuck = new Button("Change Luck",
                eventCoin-> logic.changeRandom());

        HorizontalLayout top = new HorizontalLayout(numberField, faceSelect, bonusField);
        HorizontalLayout mid = new HorizontalLayout(btnAdv, btnDis, btnCoin);
        HorizontalLayout btns = new HorizontalLayout(btnRoll, btnClear, btnLuck);
        HorizontalLayout bot = new HorizontalLayout(fieldResults);
        HorizontalLayout detailsBot = new HorizontalLayout(textDetails);

        add(top, mid, btns, bot, detailsBot);

        setVisible(false);
    }

    public void show() {
        setVisible(!isVisible());
    }

    private void calc() {
        textDetails.setText("");
        int result = 0;

        int inputNumber = numberField.getValue();

        int inputFace = 0;
        if (faceSelect.getValue() == null) {
            textDetails.setText("Error");
        } else {
            inputFace = faceSelect.getValue();
        }

        int inputBonus = bonusField.getValue();

        result = logic.rollDice(inputNumber, inputFace, inputBonus);

        displayResults(result);
    }

    private void rollAdv() {
        int advResult = logic.rollAdv(
                bonusField.getValue()
        );
        fieldResults.setValue(String.valueOf(advResult));
        String advDetails = "Rolled 2d20 with advantage. Result: %d";
        textDetails.setText(
                String.format(advDetails, advResult)
        );
    }

    private void rollDis() {
        int disResult = logic.rollDis(
                bonusField.getValue()
        );
        fieldResults.setValue(String.valueOf(disResult));
        String disDetails = "Rolled 2d20 with disadvantage. Result: %d";
        textDetails.setText(
                String.format(disDetails, disResult)
        );
    }

    private void flip() {
        int coin = this.logic.flipCoin();
        if (coin == 1) {
            this.fieldResults.setValue("Heads");
            this.textDetails.setText("Flipped a coin. Landed on heads.");
        } else {
            this.fieldResults.setValue("Tails");
            this.textDetails.setText("Flipped a coin. Landed on tails.");
        }
    }

    private void displayResults(int result) {
        fieldResults.setValue(
                String.valueOf(result)
        );
        setDetails(result);
    }

    private void setDetails(int result) {
        String s = "Rolled a d%d, %d of times, with a %d bonus with the result: %d";
        String details = String.format(s, faceSelect.getValue(), numberField.getValue(), bonusField.getValue(), result);
        textDetails.setText(details);
    }

    private void clear() {
        fieldResults.setValue("");
        textDetails.setText("");
        numberField.setValue(1);
        bonusField.setValue(0);
    }
}
