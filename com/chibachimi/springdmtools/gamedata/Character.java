package com.chibachimi.springdmtools.gamedata;

// TODO Refine and expand this class for later use
// I know this looks bad BUT it will be better later... and will still look bad!
public class Character {

    private String name;
    private int currentRoll;
    private String rollAsString;
    private int bonus;
    private String bonusAsString;

    public Character(String name) {
        this.name = name;
        this.currentRoll = 0;
        this.rollAsString = "";
        this.bonus = 0;
        this.bonusAsString = "";
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

    public int getBonus() {
        return this.bonus;
    }
    public String getBonusAsString() {
        return this.bonusAsString;
    }

    public void setCurrentRoll(int currentRoll) {
        this.currentRoll = currentRoll;
    }

    public void setRollAsString(String rollAsString) {
        this.rollAsString = rollAsString;

        if (rollAsString.equals("Natural 1")) {

            setCurrentRoll(1);
        } else if (rollAsString.equals("Natural 20")) {

            setCurrentRoll(20);
        } else {

            setCurrentRoll(Integer.parseInt(rollAsString));
        }
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
    public void setBonusAsString(String bonusAsString) {
        setBonus(Integer.parseInt(bonusAsString));
        this.bonusAsString = bonusAsString;
    }

    public static Character tempChar(String name) {
        return new Character(name);
    }
}
