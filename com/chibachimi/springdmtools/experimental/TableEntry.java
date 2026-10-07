package com.chibachimi.springdmtools.experimental;

// TODO Simple, should have two strings for now
public class TableEntry {
    String entryOne;
    String entryTwo;

    public TableEntry(String e1, String e2) {
        this.entryOne = e1;
        this.entryTwo = e2;
    }

    public String getEntryOne() {
        return entryOne;
    }

    public void setEntryOne(String entryOne) {
        this.entryOne = entryOne;
    }

    public String getEntryTwo() {
        return entryTwo;
    }

    public void setEntryTwo(String entryTwo) {
        this.entryTwo = entryTwo;
    }
}
