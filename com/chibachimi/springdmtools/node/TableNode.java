package com.chibachimi.springdmtools.node;

import com.chibachimi.springdmtools.createdfiles.Defaults;
import com.chibachimi.springdmtools.experimental.TableEntry;
import com.chibachimi.springdmtools.nodehandling.NodeDeleter;
import com.chibachimi.springdmtools.nodehandling.NodeWriter;
import com.vaadin.flow.component.textfield.TextField;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class TableNode extends NodeItem {
    String diceType;
    String[] fields;
    int numOfFields;

    ArrayList<TableEntry> entries;

    static String subfolder;


    public TableNode(
            String name,
            String diceType,
            int num,
            String[] fields,
            ArrayList<TableEntry> entries
    )
    {
        super(name);
        this.diceType = diceType;
        this.numOfFields = num;
        this.fields = fields;
        this.entries = entries;
    }

    public TableNode() {
        super("Empty");
        this.diceType = "";
        this.numOfFields = 1;
        this.fields = new String[]{};
        this.entries = new ArrayList<>();
    }

    public boolean isBlank() {
        return  this.name.isBlank() ||
                this.fields.length == 0 ||
                this.diceType.isBlank() ||
                this.entries.isEmpty();
    }

    public String getDiceType() {
        return diceType;
    }

    public String[] getFields() {
        return fields;
    }

    public ArrayList<TableEntry> getEntries() {
        return entries;
    }

    public void savePrep(String name,
                         String type,
                         String[] fields,
                         int fieldCount,
                         ArrayList<TextField[]> textFields)
    {
        this.name = name;
        this.diceType = type;
        this.fields = fields;
        this.numOfFields = fieldCount;

        // TODO Add the subfolder to the path thing
//        super.path = String.valueOf(Paths.get(
//                Defaults.getTablesAsString(),
//                super.name + ".json"
//        ));
        super.path = String.valueOf(Paths.get(subfolder, String.valueOf(File.separatorChar), super.name + ".json"));

        ArrayList<TableEntry> changed = new ArrayList<>();

        for (TextField[] array : textFields) {
            TableEntry e = new TableEntry(array[0].getValue(), array[1].getValue().split(","));
            changed.add(e);
        }

        this.entries = changed;

        System.out.println("Saving to: " + super.getPath());
        save();
    }

    public static void setSubfolder(String p) {
        subfolder = p;
    }
    @Override
    public void save() {
        NodeWriter writer = new NodeWriter(this);
        writer.save();
    }

    @Override
    public void delete() {
        NodeDeleter nodeDeleter = new NodeDeleter(this.path);
        nodeDeleter.delete();
    }

    @Override
    public void setPath(String path) {
        super.setPath(path);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getPath() {
        return super.getPath();
    }
}
