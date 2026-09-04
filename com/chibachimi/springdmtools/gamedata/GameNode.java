package com.chibachimi.springdmtools.gamedata;

import com.chibachimi.springdmtools.createdfiles.Defaults;
import com.chibachimi.springdmtools.filehandling.GameDeleter;
import com.chibachimi.springdmtools.filehandling.GameWriter;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GameNode {

    private String name;
    private ArrayList<String> playerList;
    private ArrayList<String> characterList;
    private String path;

    public GameNode(String name) {
        this.name = name;
        this.playerList = new ArrayList<>();
        this.characterList = new ArrayList<>();
    }

    public void addPlayer(String playerName) {
        this.playerList.add(playerName);
    }

    public final void save(String n, ArrayList<String> pn, ArrayList<String> cn) {
       // TODO Skip checks for now, just write. This isn't very smart but it will get smarterer later
        changeName(n);
        this.path = String.valueOf(Paths.get(
                Defaults.getGamesPathAsString(),
                this.name + ".json"
        ));
        changePlayerNames(pn);
        changeCharacters(cn);
        GameWriter writer = new GameWriter().saveGame(this);
    }

    private void changePlayerNames(List<String> list) {
            this.playerList = new ArrayList<>(list);
    }

    private void changeCharacters(List<String> list) {
        this.characterList = new ArrayList<>(list);
    }

    // Delete itself from the disk
    public void delete() {
        GameDeleter deleter = new GameDeleter(this.path);
        deleter.delete();
    }

    // Getters and Setters
    public ArrayList<String> getPlayerList() {
        return playerList;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String pathAsString) {
        this.path = pathAsString;
    }

    public String getPlayerNamesAsString() {
        StringBuilder builder = new StringBuilder();
        // I think this is redundant bc Java checks for us
        if (playerList.isEmpty() || playerList == null) {
            builder.append("");
        }
        for (String player : playerList) {
            builder.append(player).append(", ");
        }
        return builder.toString();
    }

    public String getCharactersAsString() {
        StringBuilder builder = new StringBuilder();
        for (String character : characterList) {
            builder.append(character).append(", ");
        }
        return builder.toString();
    }

    public ArrayList<Character> getCharactersAsCharacters() {
        ArrayList<Character> characters = new ArrayList<>();
        for (String name : characterList) {
            characters.add(new Character(name));
        }
        return characters;
    }

    public ArrayList<String> getCharactersAsList() {
        return characterList;
    }
}
