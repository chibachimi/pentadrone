package com.chibachimi.springdmtools.experimental;

public class TableEntry {
    String entryDice;
    String[] entries;

    int counter = 0;

    public TableEntry(String e1, String[] e2) {
        this.entryDice = e1;
        this.entries = e2;
    }

    public TableEntry() {
        this.entryDice = "";
        this.entries = new String[]{};
    }

    public String getEntryDice() {
        return entryDice;
    }

    public void setEntryDice(String entryDice) {
        this.entryDice = entryDice;
    }

    public String[] getEntries() {
        return entries;
    }

    public void setEntries(String[] entries) {
        this.entries = entries;
    }

    // TODO Needs a redo with TableNode getEntries()
    public String getEntry() {
        String copy = entries[counter];

        if (counter < entries.length) {
            counter += 1;
        }

        return copy;
    }
}
