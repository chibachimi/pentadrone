package com.chibachimi.springdmtools.ui.views;

import com.chibachimi.springdmtools.createdfiles.Defaults;
import com.chibachimi.springdmtools.experimental.TableView;
import com.chibachimi.springdmtools.node.TableNode;
import com.chibachimi.springdmtools.nodehandling.NodeReader;
import com.chibachimi.springdmtools.ui.components.HelpDialog;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@Route("/table-master")
public class TableMasterView extends VerticalLayout {
    NodeReader reader;
    ArrayList<File> files;

    public TableMasterView() {
        files = readTableGroups();

        Button btnHelp = new Button("Help", e-> {
            Dialog helpDialog = makeHelp();
            helpDialog.open();
        });
        Button btnNewGroup = new Button("Create New Table Group",
                event -> {
            Dialog dialog = dialogPopup();
            dialog.open();
        });
        // TODO Please refactor this LMAO
        Button btnRemove = new Button("Remove Table Group", event -> {
            Dialog deleteDialog = new Dialog("Delete Table Group?");
            TextField fieldDelete = new TextField("Name of group to delete");
            fieldDelete.setPlaceholder("Name");
            Button btnCancel = new Button("Cancel", event1 -> {
                deleteDialog.close();
            });
            Button btnConfirm = new Button("Confirm", event1 -> {
                boolean d = false;
                if (!fieldDelete.isEmpty()) {
                    File toDelete = new File(
                            Defaults.getTablesAsString() +
                                    File.separator +
                                    fieldDelete.getValue()
                    );
                    System.out.println(toDelete.getPath());
                    d = deleteFiles(toDelete);
                }
                // Popups depending on success
                if (d) {
                    Notification notifSucc = new Notification("Group successfully deleted. Please refresh the page.", 2000, Notification.Position.MIDDLE);
                    notifSucc.open();
                    deleteDialog.close();
                } else {
                    Notification notifErr = new Notification("Error deleting group.", 2000, Notification.Position.MIDDLE);
                    notifErr.open();
                }
            });
            deleteDialog.add(fieldDelete);
            deleteDialog.getFooter().add(btnCancel, btnConfirm);
            deleteDialog.open();
        });

        // TODO Do this later then add this back
        Button btnExport = new Button("Export Tables");

        add(btnHelp, btnNewGroup, btnRemove, new Text("Your Tables: \n"));

        if (files.isEmpty() || files == null) {
            add(new Text("No tables detected! Click 'Create New Table Group' to get started."));
        }

        ArrayList<Button> btns = new ArrayList<>();
        for (File file : files) {
            Button btn = new Button(file.getName(), event -> {
                TableView.setLoadedPath(file.getAbsolutePath());
                TableNode.setSubfolder(file.getAbsolutePath());
                UI.getCurrentOrThrow().navigate(TableView.class);
            });
            btns.add(btn);
        }
        for (Button b : btns) {
            add(b);
        }
    }

    // Method shamelessly stolen from Baeldung lmao
    private boolean deleteFiles(File toDelete) {
        File[] contents = toDelete.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteFiles(f);
            }
        }
        return toDelete.delete();
    }

    private Dialog dialogPopup() {
        Dialog d = new Dialog("New Table Group");
        TextField nameField = new TextField("Name");
        Text text = new Text("");
        Button btnConfirm = new Button("Confirm",
                VaadinIcon.CHECK.create(),
                event -> {
            createGroup(nameField.getValue());
                    Notification notif = new Notification("Group created. Please refresh page.", 2000, Notification.Position.MIDDLE);
                    notif.open();
                    d.close();
                });
        d.add(nameField, text, btnConfirm);
        return d;
    }

    private void createGroup(String name) {
        String groupPath = Defaults.getTablesAsString();
        File newGroup = new File(Paths.get(groupPath, name).toString());
        if (newGroup.mkdir()) {
            System.out.println("New group created at " + newGroup.getAbsolutePath());
        } else {
            System.err.println("New group failed to be created at " + newGroup.getAbsolutePath());
        }
    }

    private Dialog makeHelp() {
        String helpTitle = "Table Master View Help";
        String helpText = """
                The "Create New Table Group" button creates a new folder where your tables will be stores.
                
                The "Remove Table Group" button displays a popup where you will be prompted to
                enter the name of the group you would like to completely remove. This is
                not reversible and will delete the files inside.
                
                Your created groups will be displayed along the side of the screen. Clicking one will bring you to the
                appropriate table view.
                """;

        HelpDialog helpDialog = new HelpDialog(helpTitle, helpText);
        return helpDialog.getDialog();
    }

    private ArrayList<File> readTableGroups() {
        String path = Defaults.getTablesAsString();
        File headFile = new File(path);

        return new ArrayList<>(Arrays.stream(
                headFile.getAbsoluteFile().listFiles()
        ).toList());
    }
}
