package com.chibachimi.springdmtools.ui.views;

import com.chibachimi.springdmtools.filehandling.GameReader;
import com.chibachimi.springdmtools.gamedata.GameNode;
import com.chibachimi.springdmtools.ui.components.HelpDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("/games")
public class GameView extends VerticalLayout {
    HorizontalLayout buttonHolder;
    Button buttonAdd;
    Button buttonEdit;

    GameNode selectedGame = null;

    private final List<GameNode> gameList;
    private final Grid<GameNode> grid;
    protected final GameEditor editor;

    public GameView(GameEditor editor) {
        GameReader reader = new GameReader();
        this.editor = editor;

        Button buttonHelp = makeHelpButton();

        buttonAdd = new Button(
                "Create A New Game",
                VaadinIcon.PLUS.create(),
                e -> createGame()
        );
        buttonEdit = new Button(
                "Edit Selected Game",
                VaadinIcon.BOOK.create(),
                e -> editGame()
        );
        buttonHolder = new HorizontalLayout(buttonAdd, buttonEdit);

        gameList = reader.getGamesList();
        for (GameNode game : gameList) System.out.println(game.getName());

        grid = new Grid<>(GameNode.class, false);
        // IntelliJ says this isn't used but don't trust it.
        Grid.Column<GameNode> nameCol = grid.addColumn(GameNode::getName).setHeader("Game");
        grid.setItems(gameList);

        grid.addSelectionListener(e -> {
            if (e.getFirstSelectedItem().isPresent()) selectedGame = e.getFirstSelectedItem().get();
        });

        add(buttonHelp, grid, buttonHolder, editor);
    }

    private void editGame() {
        if (selectedGame == null) {
            System.err.println("No game selected!");
        }
        editor.editGame(selectedGame);
    }

    private void createGame() {
        gameList.add(new GameNode("New Game"));
        grid.getDataProvider().refreshAll();
    }

    private Button makeHelpButton() {
        HelpDialog helpDialog = new HelpDialog(
                "Games",
                """
                This will display a grid of games taken from the games/ folder.
                
                To create a new game, press the "Create A New Game" button. This will add a game to the list.
                
                You can export games to your Downloads/ folder using the "Export Games" button.
                
                To edit a game, new or not, select it in the grid then press the "Edit Selected Game" button.
                This will bring up a submenu where you can change the name, add or remove players, and add or remove characters.
                Save your game pressing the "Save" button, cancel any changes using the "Cacnel" button.
                To delete the game, press the "Delete Game" button, which will then pop up a confirmation.
                *This is ireversable*.
                
                Games are stored as json files in your games/ folder.
                """
        );
        return new Button("Help", e -> helpDialog.getDialog().open());
    }
}
