package com.chibachimi.springdmtools.logic;

public enum DiceType {
    d4("d4"),
    d6("d6"),
    d8("d8"),
    d10("d10"),
    d12("d12"),
    d20("d20"),
    d100("d100");

    private final String asString;

    DiceType(String asString) {
        this.asString = asString;
    }

    public String getAsString() {
        return asString;
    }
}
