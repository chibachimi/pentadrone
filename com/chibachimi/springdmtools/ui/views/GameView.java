package com.chibachimi.springdmtools.ui.views;

import com.chibachimi.springdmtools.filehandling.GameReader;
import com.chibachimi.springdmtools.gamedata.GameNode;
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

        add(grid, buttonHolder, editor);
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
}
