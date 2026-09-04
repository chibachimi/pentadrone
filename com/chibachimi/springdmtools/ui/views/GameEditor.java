package com.chibachimi.springdmtools.ui.views;

import com.chibachimi.springdmtools.gamedata.GameNode;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;

@SpringComponent
@UIScope
public class GameEditor extends VerticalLayout {
    GameNode game;

    Button buttonSave;
    Button buttonClose;
    Button buttonDelete;

    TextField fieldPlayers = new TextField("Add Players (Separate players with a comma)");
    TextField fieldName = new TextField("Change Game Name");
    TextField fieldCharacters = new TextField("Characters");

    HorizontalLayout buttonHolder;

    @Autowired
    public GameEditor() {
        buttonSave = new Button("Save",
                VaadinIcon.CHECK.create(),
                e -> save());
        buttonClose = new Button("Cancel",
                VaadinIcon.EXIT.create(),
                e -> cancel());
        buttonDelete = new Button("Delete Game",
                VaadinIcon.TRASH.create(),
                e -> delete());

        buttonHolder = new HorizontalLayout(buttonClose, buttonSave, buttonDelete);

        add(fieldName, fieldPlayers, fieldCharacters);
        add(buttonHolder);

        setVisible(false);
    }

    public void editGame(GameNode game) {
        if (game == null) {
            setVisible(false);
            return;
        }
        this.game = game;

        fieldName.setValue(game.getName());
        fieldPlayers.setValue(game.getPlayerNamesAsString());
        fieldCharacters.setValue(game.getCharactersAsString());

        setVisible(true);
    }

    private void save() {
        game.save(fieldName.getValue(), getNamesAsList(), getValuesAsList(fieldCharacters));
        Dialog dialogSaveAlert = makeDialogSaveAlert();
        dialogSaveAlert.open();
    }

    private void cancel() {
        setVisible(false);
        game = null;
    }

    private void delete() {
        Dialog dialogConfirm = makeDialogDelete();
        add(dialogConfirm);
        dialogConfirm.open();
    }

    private ArrayList<String> getNamesAsList() {
        String fullText = fieldPlayers.getValue();
        return new ArrayList<>(Arrays.stream(fullText.split("\\s*,\\s*")).toList());
    }

    private ArrayList<String> getValuesAsList(TextField field) {
        String fullText = field.getValue();
        return new ArrayList<>(Arrays.stream(fullText.split("\\s*,\\s*")).toList());
    }

    private Dialog makeDialogDelete() {
        Dialog d = new Dialog();
        Button buttonCancel = new Button("Cancel",
                e -> d.close());
        Button buttonConfirm = new Button("Confirm",
                VaadinIcon.TRASH.create());
        buttonConfirm.addClickListener(e -> {
            game.delete();
            d.close();
        });
        d.getHeader().add("Are you sure you wish to delete " + game.getName() + " ?");
        d.getFooter().add(buttonCancel, buttonConfirm);
        return d;
    }

    private Dialog makeDialogSaveAlert() {
        Dialog d = new Dialog();
        Button buttonConfirm = new Button("Okay",
                VaadinIcon.CHECK.create(),
                e -> d.close());
        d.getHeader().add(
                "Game" + game.getName() + " has been saved to: " + game.getPath()
        );
        d.getFooter().add(buttonConfirm);
        return d;
    }
}
