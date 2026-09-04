package com.chibachimi.springdmtools.ui.views;

import com.chibachimi.springdmtools.filehandling.GameReader;
import com.chibachimi.springdmtools.gamedata.GameNode;
import com.chibachimi.springdmtools.ui.components.HelpDialog;
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

    private Optional<Character> optionalCharacter;
    ArrayList<Character> characters = new ArrayList<>();

    public InitiativeView() {
        Button buttonHelp = makeHelpButton();

        GameReader reader = new GameReader();
        var games = reader.getGamesList();


        // Add an empty game first. This is loaded by default.
        GameNode empty = new GameNode("Empty");
        games.addFirst(empty);

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
        // Default set the grid to this empty game
        this.characters = new ArrayList<>(empty.getCharactersAsCharacters());
        initGrid.setItems(this.characters);

        setAlignItems(Alignment.CENTER);

        HorizontalLayout gameButtonHolder = new HorizontalLayout();

        for (GameNode g : games) {
            gameButtonHolder.add(new Button(g.getName(), e -> {
                this.characters = new ArrayList<>(g.getCharactersAsCharacters());
                System.out.println(this.characters);
                initGrid.setItems(this.characters);
            }));
        }

        HorizontalLayout interactionHolder = new HorizontalLayout(
                new Button(new Button("Add New Character", e -> {
                    characters.add(Character.tempChar("New Character"));
                    initGrid.getDataProvider().refreshAll();
                })),
                new Button(new Button("Remove Selected Character", e -> {
                    optionalCharacter.ifPresent(this.characters::remove);
                    initGrid.getDataProvider().refreshAll();
                }))
        );

        add(buttonHelp, gameButtonHolder, initGrid, interactionHolder);
    }

    private Button makeHelpButton() {
        HelpDialog helpDialog = new HelpDialog(
                "Initiative",
                """
                        This will display a line of buttons and a grid.
                        
                        By default, an empty list is loaded.
                        
                        To populate the grid, select a button with one of your games on it.
                        This will place all of the characters from your game onto the grid. By default, everyone has an initiative of zero."
                        
                        Double click on a character to change their initiative value or their name. Once done, press the arrows above the initiative column twice.
                        This will auto sort everyone listed.
                        
                        
                        To add a new character (or monster), click the "Add A New Character" button.
                        To remove a character, click on a character on the grid, then press the the "Remove Selected Character" button.
                        """
        );
        return new Button("Help", e -> helpDialog.getDialog().open());
    }
}
