package com.chibachimi.springdmtools.gamedata;

// TODO Refine and expand this class for later use
// I know this looks bad BUT it will be better later... and will still look bad!
public class Character {

    private String name;
    private int currentRoll;
    private String rollAsString;

    public Character(String name) {
        this.name = name;
        currentRoll = 0;
        rollAsString = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentRoll() {
        return currentRoll;
    }

    public String getRollAsString() {
        return rollAsString;
    }

    public void setCurrentRoll(int currentRoll) {
        this.currentRoll = currentRoll;
    }

    public void setRollAsString(String rollAsString) {
        setCurrentRoll(Integer.parseInt(rollAsString));
        this.rollAsString = rollAsString;
    }

    public static Character tempChar(String name) {
        return new Character(name);
    }
}
