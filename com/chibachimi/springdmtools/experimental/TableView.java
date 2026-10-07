package com.chibachimi.springdmtools.experimental;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.CellFocusEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

import java.util.Optional;

@Route("/table")
public class TableView extends VerticalLayout {

//    private final Table[] loadedTables;

    TableEntry selectedEntry;

    public TableView() {
        // TODO Can actually load multiple tables
        // TODO Will need a way to load these tables

        Table testTable = new Table();

        Grid<TableEntry> tableGrid = new Grid<>(TableEntry.class, false);
        Binder<TableEntry> binder = new Binder<>(TableEntry.class);
        Editor<TableEntry> editor = tableGrid.getEditor();
        editor.setBinder(binder);

        Grid.Column<TableEntry> colDice = tableGrid.addColumn(TableEntry::getEntryOne)
                .setHeader(testTable.getDiceType())
                .setWidth("7rem")
                .setFlexGrow(0);

        TextField fieldDice = new TextField();
        binder.forField(fieldDice)
                .bind(TableEntry::getEntryOne, TableEntry::setEntryOne);
        colDice.setEditorComponent(fieldDice);

        Grid.Column<TableEntry> colField = tableGrid.addColumn(TableEntry::getEntryTwo)
                .setHeader(testTable.getField());

        TextField fieldField = new TextField();
        binder.forField(fieldField)
                .bind(TableEntry::getEntryTwo, TableEntry::setEntryTwo);
        colField.setEditorComponent(fieldField);

        // TODO Do we need this?
//        tableGrid.addItemClickListener(singleClickEvent -> {
//            selectedEntry = singleClickEvent.getItem();
//        });

        tableGrid.addItemDoubleClickListener(clickEvent -> {
            editor.editItem(clickEvent.getItem());
            Component editorComponent = clickEvent.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable) {
                ((Focusable<?>) editorComponent).focus();
            }

            tableGrid.getDataProvider().refreshAll();
        });

        tableGrid.setItems(testTable.getEntries());
        tableGrid.getDataProvider().refreshAll();

        Button btnEdit = new Button("Edit Selected Row");
        Button btnSave = new Button("Save Table");
        HorizontalLayout btnHolder = new HorizontalLayout(btnEdit, btnSave);
        add(btnHolder);

        add(new Text(testTable.getTitle()), tableGrid);

        setAlignItems(Alignment.CENTER);
    }
}
