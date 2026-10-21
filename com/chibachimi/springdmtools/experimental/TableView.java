package com.chibachimi.springdmtools.experimental;

import com.chibachimi.springdmtools.node.TableNode;
import com.chibachimi.springdmtools.ui.components.HelpDialog;
import com.google.gson.Gson;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

@Route("/table")
public class TableView extends VerticalLayout {
    private ArrayList<TableNode> loadedTables;
    private ArrayList<Grid<TableEntry>> grids = new ArrayList<>();

    private HorizontalLayout btnHolder;

    private static String loadedPath;

    public TableView() {
        loadedTables = loadTables();

        if (loadedTables.isEmpty()) {
            loadedTables.add(new TableNode());
        }

        makeTableGrid();
        makeTables();

        // TODO FINISH HELP BUTTON
        makeButtons();
        add(btnHolder);

        for (Grid<TableEntry> grid : grids) {
            this.add(grid);
        }

        setAlignItems(Alignment.CENTER);
    }

    private void makeTableGrid() {
        Grid<TableNode> tableListGrid = new Grid<>(TableNode.class, false);
        tableListGrid.setItems(loadedTables);
        Grid.Column<TableNode> colName = tableListGrid.addColumn(TableNode::getName)
                .setHeader("Table Name");
        add(tableListGrid);

        GridContextMenu<TableNode> tableContextMenu = tableListGrid.addContextMenu();
        tableContextMenu.addItem("Edit", e->{
            e.getItem().ifPresent(t -> {
                TableEditor tableEditor = new TableEditor(t);
            });
        });
    }

    private void makeTables() {
        for (TableNode currentTable : loadedTables) {
            Grid<TableEntry> tableGrid = new Grid<>(TableEntry.class, false);
            Binder<TableEntry> binder = new Binder<>(TableEntry.class);
            Editor<TableEntry> editor = tableGrid.getEditor();
            editor.setBinder(binder);

            Grid.Column<TableEntry> colDice = tableGrid.addColumn(TableEntry::getEntryDice)
                    .setHeader(currentTable.getDiceType())
                    .setWidth("7rem")
                    .setFlexGrow(0);

            for (String field : currentTable.getFields()) {
                Grid.Column<TableEntry> colField = tableGrid.addColumn(TableEntry::getEntry)
                        .setHeader(field);
            }

            tableGrid.setItems(currentTable.getEntries());
            tableGrid.getDataProvider().refreshAll();

            grids.add(tableGrid);
        }
    }

    private void makeButtons() {
        Button btnHelp = new Button("Help", e -> {
            Dialog helpDialog = makeHelp();
            helpDialog.open();
        });
        Button btnRefresh = new Button("Reload Grid", event -> {
            loadedTables = loadTables();
            for (Grid grid : grids) {
                grid.getDataProvider().refreshAll();
            }
        });

        Button btnNew = new Button("Add New Table", event -> {
            TableEditor tableEditor = new TableEditor();
        });

        Button btnSave = new Button("Save Tables", event -> {
            loadedTables.forEach(TableNode::save);
        });

        btnHolder = new HorizontalLayout(btnHelp, btnNew, btnSave);
    }

    // TODO This needs to be refactored to make it better organized and clearer.
    public ArrayList<TableNode> loadTables() {
        ArrayList<TableNode> tempLoad = new ArrayList<>();
        Gson gson = new Gson();

        File tableFile = new File(loadedPath);

        File[] subFiles = tableFile.listFiles();
        if (subFiles == null) {
            return tempLoad;
        }

        for (File f : subFiles) {
            try {
                String json = Files.readString(Path.of(f.getAbsolutePath()), Charset.defaultCharset());
                System.out.println(json);
                TableNode table = gson.fromJson(json, TableNode.class);

                tempLoad.add(table);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return tempLoad;
    }

    private Dialog makeHelp() {
        String helpTitle = "Table View Help";
        String helpText = """
                The top grid on the page will display all of the tables loaded in the group.
                
                Right click a table in this grid and select "Edit" to bring up the table editor menu.
                
                Below are a row of utility buttons. "Add New Table" will create a new blank table and will open it
                inside of the table editor menu. The "Save Tables" button will save all of the tables as they currently are.
                
                The grids below will be the tables you have created.
                """;

        HelpDialog helpDialog = new HelpDialog(helpTitle, helpText);
        return helpDialog.getDialog();
    }

    public static void setLoadedPath(String path) {
        loadedPath = path;
    }
}
