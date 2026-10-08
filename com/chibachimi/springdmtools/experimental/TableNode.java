package com.chibachimi.springdmtools.experimental;

import java.util.ArrayList;

// TODO Extrapolate from this single table
// TODO This is only a single table
public class TableNode extends NodeItem {
    String diceType;
    String field;

    ArrayList<TableEntry> entries;


    public TableNode(
            String name,
            String diceType,
            String field,
            ArrayList<TableEntry> entries
    )
    {
        super(name);
        this.diceType = diceType;
        this.field = field;
        this.entries = entries;
    }

    public TableNode() {
        super("Blank");
        this.diceType = "";
        this.field = "";
        this.entries = new ArrayList<>();
    }

    public boolean isBlank() {
        return  this.name.isBlank() ||
                this.field.isBlank() ||
                this.diceType.isBlank() ||
                this.entries.isEmpty();
    }

    public String getDiceType() {
        return diceType;
    }

    public String getField() {
        return field;
    }

    public ArrayList<TableEntry> getEntries() {
        return entries;
    }

    // TODO Down here we prep things for saving
    @Override
    public void save() {

    }

    @Override
    public void delete() {

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
