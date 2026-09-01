package com.chibachimi.springdmtools.ui.views;

import com.chibachimi.springdmtools.filehandling.GameReader;
import com.chibachimi.springdmtools.gamedata.GameNode;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.chibachimi.springdmtools.gamedata.Character;

import java.util.ArrayList;
import java.util.Optional;

@Route("/initiative")
public class InitiativeView extends VerticalLayout {

    int itemCount;
    private Optional<Character> optionalCharacter;
    ArrayList<Character> characters = new ArrayList<>();

    public InitiativeView() {
        GameReader reader = new GameReader();
        var games = reader.getGamesList();

        Grid<Character> initGrid = new Grid<>(Character.class, false);
        Binder<Character> binder = new Binder<>(Character.class);
        Editor<Character> editor = initGrid.getEditor();
        editor.setBinder(binder);

        Grid.Column<Character> nameColumn = initGrid.addColumn(Character::getName).setHeader("Character");
        Grid.Column<Character> rollColumn = initGrid.addColumn(Character::getCurrentRoll).setHeader("Roll")
                .setSortable(true)
                .setComparator(Character::getCurrentRoll);

        TextField nameField = new TextField();
        binder.forField(nameField)
                .asRequired("This character needs a name!")
                .bind(Character::getName, Character::setName);
        nameColumn.setEditorComponent(nameField);

        initGrid.addItemDoubleClickListener(e -> {
           editor.editItem(e.getItem());
            Component editorComponent = e.getColumn().getEditorComponent();
           if (editorComponent instanceof Focusable) {
               ((Focusable<?>) editorComponent).focus();
           }
        });

        TextField rollField = new TextField();
        binder.forField(rollField)
                .asRequired("This character needs a roll!")
                .bind(Character::getRollAsString, Character::setRollAsString);
        rollColumn.setEditorComponent(rollField);

        initGrid.addItemDoubleClickListener(e -> {
            editor.editItem(e.getItem());
            Component editorComponent = e.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable) {
                ((Focusable<?>) editorComponent).focus();
            }});

        initGrid.addSelectionListener(e ->
            optionalCharacter = e.getFirstSelectedItem()
        );

        setAlignItems(Alignment.CENTER);

        HorizontalLayout gameButtonHolder = new HorizontalLayout();

        for (GameNode g : games) {
            gameButtonHolder.add(new Button(g.getName(), e -> {
                this.characters = new ArrayList<>(g.getCharactersAsCharacters());
                System.out.println(this.characters);
                initGrid.setItems(this.characters);
            }));
        }

        add(gameButtonHolder, initGrid);

        add(new Button("Add New Character", e -> {
            itemCount++;
            characters.add(Character.tempChar("Item " + itemCount));
            initGrid.getDataProvider().refreshAll();
        }));

        add(new Button("Remove Selected Character", e -> {
            itemCount--;
            optionalCharacter.ifPresent(this.characters::remove);
            initGrid.getDataProvider().refreshAll();
        }));
    }
}
