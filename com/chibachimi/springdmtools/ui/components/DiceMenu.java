package com.chibachimi.springdmtools.ui.components;

import com.chibachimi.springdmtools.logic.DiceLogic;
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
        numberField.setTooltipText("The number of times the dice are rolled.");

        faceSelect = new Select<>("Faces");
        faceSelect.setPlaceholder("Select Dice");
        faceSelect.setItems(4, 6, 8, 10, 12, 20, 100);
        faceSelect.setTooltipText("The type of dice to roll.");

        bonusField = new IntegerField("Bonus");
        bonusField.setValue(0);
        Div bonusDiv = new Div("+");
        bonusField.setPrefixComponent(bonusDiv);
        bonusField.setTooltipText("The bonus that will be added to the final dice roll.");

        fieldResults = new TextField("Results");
        fieldResults.setTooltipText("Results of the rolls are displayed here.");

        textDetails = new Text("");

        Button btnAdv = new Button("Roll Advantage",
                ea->rollAdv());
        btnAdv.setTooltipText("Rolls 2d20 and returns the highest. Adds any bonus present.");
        Button btnDis = new Button("Roll Disadvantage",
                ed->rollDis());
        btnDis.setTooltipText("Rolls 2d20 and returns the lowest. Adds any bonus present.");
        Button btnCoin = new Button("Flip Coin",
                event->flip());
        btnCoin.setTooltipText("Rolls a 0 or 1. 1 is heads and 0 is tails.");

        Button btnRoll = new Button("Roll!",
                event1-> calc());
        btnRoll.setTooltipText("Rolls the selected dice the number of times specified. Then adds the specified bonus.");
        Button btnClear = new Button("Clear",
                event2-> clear());
        btnClear.setTooltipText("Clears the number and bonus fields.");
        Button btnLuck = new Button("Change Luck",
                eventLuck-> {
                    logic.changeRandom();
                    textDetails.setText("Your luck has been changed.");
                });
        btnLuck.setTooltipText("Generates a new random for the dice rolls.");

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
        String result;

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
        String advResult = logic.rollAdv(
                bonusField.getValue()
        );
        fieldResults.setValue(advResult);
        String advDetails = "Rolled 2d20 with advantage. Result: %s";
        textDetails.setText(
                String.format(advDetails, advResult)
        );
    }

    private void rollDis() {
        String disResult = logic.rollDis(
                bonusField.getValue()
        );
        fieldResults.setValue(disResult);
        String disDetails = "Rolled 2d20 with disadvantage. Result: %s";
        textDetails.setText(
                String.format(disDetails, disResult)
        );
    }

    private void flip() {
        String coin = this.logic.flipCoin();
        if (coin.equals("1")) {
            this.fieldResults.setValue("Heads");
            this.textDetails.setText("Flipped a coin. Landed on heads.");
        } else {
            this.fieldResults.setValue("Tails");
            this.textDetails.setText("Flipped a coin. Landed on tails.");
        }
    }

    private void displayResults(String result) {
        fieldResults.setValue(result);
        setDetails(result);
    }

    private void setDetails(String result) {
        String details = "";

        if (result.equals("Natural 20")) {
            details = "Congratulations! A Natural 20!";
        }
        if (result.equals("Natural 1")) {
            details = "You rolled a Natural 1.";
        } else {
            String s = "Rolled a d%d, %d of times, with a %d bonus with the result: %s";
            details = String.format(s, faceSelect.getValue(), numberField.getValue(), bonusField.getValue(), result);
        }

        textDetails.setText(details);
    }

    private void clear() {
        fieldResults.setValue("");
        textDetails.setText("");
        numberField.setValue(1);
        bonusField.setValue(0);
    }
}
