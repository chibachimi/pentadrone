package com.chibachimi.springdmtools.experimental;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

// TODO Honestly maybe scrap this whole thing and just let the tables themselves be editable? Seems easier and more intuitive
@SpringComponent
@UIScope
public class TableEditor extends VerticalLayout {
    TableNode tableNode;

    ArrayList<TableEntry> entries;
    // Editor components
    TextField fieldTitle;

    VerticalLayout entryHolder;
    Button btnAddEntry;

    // Boilerplate buttons
    Button btnSave;
    Button btnCancel;
    Button btnDelete;

    @Autowired
    public TableEditor() {
        fieldTitle = new TextField("Title");

        btnAddEntry = new Button("Add Entry", event -> addEntry());

        // Boilerplate buttons
        btnSave = new Button("Save",
                VaadinIcon.CHECK.create(),
                event -> save()
                );
        btnCancel = new Button("Cancel",
                VaadinIcon.ARROWS_CROSS.create(),
                event -> cancel());
        btnDelete = new Button("Delete",
                VaadinIcon.TRASH.create(),
                event -> delete());

        HorizontalLayout buttonHolder = new HorizontalLayout(btnSave, btnCancel, btnDelete);

        add(fieldTitle, btnAddEntry);
        add(buttonHolder);
        //TODO Set to false later
        setVisible(true);
    }

    // TODO May need to redo this
    private void addEntry() {
        // TODO Push these to a list so we can access them later?
        TextField entry1 = new TextField();
        TextField entry2 = new TextField();
        HorizontalLayout entryHolder = new HorizontalLayout(entry1, entry2);
        add(entryHolder);
    }

    public void editTable(TableNode t) {
        if (t== null) {
            setVisible(false);
            return;
        }
        this.tableNode = t;

        // Set all the components with the current table
        fieldTitle.setValue(this.tableNode.name);

        setVisible(true);
    }

    private void save() {

    }

    private void cancel() {
        this.tableNode = null;

        setVisible(false);
    }

    private void delete() {

    }
}
