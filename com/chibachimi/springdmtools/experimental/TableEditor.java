package com.chibachimi.springdmtools.experimental;

import com.chibachimi.springdmtools.node.TableNode;
import com.chibachimi.springdmtools.ui.components.HelpDialog;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.ArrayList;
import java.util.Arrays;

@SpringComponent
@UIScope
public class TableEditor extends VerticalLayout {
    TableNode tableNode;
    ArrayList<TextField[]> textFields;

    Dialog container;
    Dialog helpDialog;

    TextField fieldTitle;
    TextField fieldType;
    TextField fieldOfFields;

    // TODO And an editing method that takes a TableNode as input
    public TableEditor(TableNode t) {
        this.tableNode = t;

        fillOutContainer();

        container.setCloseOnOutsideClick(false);
        container.setDraggable(true);
        container.open();
    }

    public TableEditor() {
        this.tableNode = new TableNode();

        fillOutContainer();

        container.setCloseOnOutsideClick(false);
        container.setDraggable(true);
        container.open();
    }

    private void fillOutContainer() {
        container = new Dialog("Table Editor");
        textFields = new ArrayList<>();

        fieldTitle = new TextField("Title", tableNode.getName());

        fieldType = new TextField("Dice");
        // TODO this is comma separated values taken from the loaded table
        fieldOfFields = new TextField("Fields");
        HorizontalLayout topHolder = new HorizontalLayout(fieldType, fieldOfFields);

        Button btnAdd = new Button("Add Entry", event -> {
            TextField e1 = new TextField();
            TextField e2 = new TextField();
            HorizontalLayout h = new HorizontalLayout(
                    e1, e2
            );
            container.add(h);
            textFields.add(new TextField[] {e1, e2});
        });

        Button btnHelp = new Button("Help", eH -> {
            Dialog helpDialog = makeHelp();
            helpDialog.open();
        });
        Button btnClose = new Button("Close/Cancel", eC -> {
            container.close();
        });
        Button btnDelete = new Button("Delete", eD -> {
            this.tableNode.delete();
            Notification notifDel = new Notification("Table deleted", 2000, Notification.Position.MIDDLE);
            notifDel.open();
            container.close();
        });
        Button btnSave = new Button("Save", eS -> {
            save();
        });
        HorizontalLayout boilerplateHolder = new HorizontalLayout(btnHelp, btnClose, btnDelete, btnSave);

        container.add(
                fieldTitle,
                btnAdd,
                topHolder,
                new Text("Add Table Entries Below")
        );

        fieldTitle.setValue(tableNode.name);
        fieldOfFields.setValue(String.join(",", tableNode.getFields()).replaceAll("\\s+", ""));
        fieldType.setValue(tableNode.getDiceType());

        // TODO Is there a better way to do this?
        for (TableEntry tableEntry : tableNode.getEntries()) {
            TextField e1 = new TextField("", tableEntry.getEntryDice(), "");
            TextField e2 = new TextField("", String.join(",", tableEntry.getEntries()).replaceAll("\\s+", ""), "");
            HorizontalLayout h = new HorizontalLayout(
                    e1, e2
            );
            textFields.add(new TextField[]{e1, e2});
            container.add(h);
        }

        container.getFooter().add(boilerplateHolder);
    }

    private void save() {
        // TODO These are just simplifications for now
        String title = fieldTitle.getValue();
        String type = fieldType.getValue();

        String[] fields = fieldOfFields.getValue().replaceAll("\\s+", "").split(",");
        System.out.println(Arrays.toString(fields));
        int fieldCount = fields.length;

        // TODO This lowkey didn't really work
        boolean check = fields.length == textFields.size();
        if (check) {
            tableNode.savePrep(
                    title,
                    type,
                    fields,
                    fieldCount,
                    this.textFields
            );
            Notification notif = new Notification("Table successfully saved", 2000, Notification.Position.MIDDLE);
            notif.open();
        } else {
            Notification notifErr = new Notification("Fields and entries do not match. Aborting save.", 2000, Notification.Position.MIDDLE);
            notifErr.open();
        }

    }

    private Dialog makeHelp() {
        String helpTitle = "Table Editor Menu";
        String helpText = """
                In the Title TextField, you can enter the title for the table you are creating or editing.
                
                The "Add Entry" button will add a new table entry below the "Add Table Entries Below" text.
                
                The "Dice" TextField is where you can specify what kind of dice will be rolled for the table.
                
                The "Fields" TextField is where you can describe the titles and number of right columns for your table.
                
                For table entries: The left TextField is for the left most column of your table. The right TextField
                should contain comma separated values that match with the fields you have described previously.
                
                Example:
                    1d4 | Encounters, Attitude
                    1   | Bandits, Hostile
                    2   | Rats, Friendly
                    3   | Goblins, Curious
                    4   | Orcs, Neutral
                This example will produce a table where you roll a 1d4 die twice, once for each column. The first roll
                will decide what we encounter, while the second will decide their attitude.
                
                The "Close/Cancel" button will close the editor.
                The "Delete" button will delete the current table.
                The "Save" button will save the table to disk.
                """;

        HelpDialog helpDialog = new HelpDialog(helpTitle, helpText);
        return helpDialog.getDialog();
    }

    // TODO Finish this lol
    private void createHelpDialog() {
        helpDialog = new Dialog("Help");
    }
}
