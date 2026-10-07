package com.chibachimi.springdmtools.experimental;

import java.util.ArrayList;

// TODO Extrapolate from this single table
// TODO This is only a single table
public class Table {
    String title;

    String diceType;
    String field;

    ArrayList<TableEntry> entries;

    private String path;

    public Table(
            String title,
            String diceType,
            String field,
            ArrayList<TableEntry> entries
    )
    {
        this.title = title;
        this.diceType = diceType;
        this.field = field;
        this.entries = entries;
    }

    public Table() {
        this.title = "Blank";
        this.diceType = "";
        this.field = "";
        this.entries = new ArrayList<>();
    }

    // TODO Remove later
    // TODO Start on the editor
    public static Table newDefaultTable() {
        var entries = new ArrayList<TableEntry>();
        entries.add(new TableEntry("1", "Bones of various merchants have been spotted in the Windswept Plains."));
        entries.add(new TableEntry("2", "F Some parts of the mosque are magically trapped"));
        entries.add(new TableEntry("3", "Some travellers have been attacked by animals on the way to the mosque recently."));
        entries.add(new TableEntry("4", "The dead must be angry. Heard some sorry sods got attacked by skeletons recently."));
        entries.add(new TableEntry("5", "F Some of those thugs at the tavern might be bandits."));
        entries.add(new TableEntry("6", "Heard there's hidden treasure in some burial site past those old ruins."));
        entries.add(new TableEntry("7", "Saw some strange rocks up north past those old ruins. Didn't look natural."));
        entries.add(new TableEntry("8", "I've seen some scouts on the Plains that look foreign."));
        entries.add(new TableEntry("9", "Saw a whole merchant caravan get slaughtered at the crossroads."));
        entries.add(new TableEntry("10", "F Saw some soldier types harass some travelers at the crossroads out west."));
        entries.add(new TableEntry("11", "Once saw a group of harpies attack some soldiers stationed by the Plains."));
        entries.add(new TableEntry("12", "Sometimes at night, I hear weird noises coming from the mosque. It scares me."));

        return new Table("Kayseri Rumors", "1d12", "Rumors", entries);
    }

    public boolean isBlank() {
        return  this.title.isBlank() ||
                this.field.isBlank() ||
                this.diceType.isBlank() ||
                this.entries.isEmpty();
    }

    public String getTitle() {
        return title;
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
}
