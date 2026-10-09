package com.chibachimi.springdmtools.gamedata;

import com.chibachimi.springdmtools.createdfiles.Defaults;
import com.chibachimi.springdmtools.filehandling.NodeDeleter;
import com.chibachimi.springdmtools.filehandling.NodeWriter;
import com.chibachimi.springdmtools.logic.NodeItem;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GameNode extends NodeItem {

    private ArrayList<String> playerList;
    private ArrayList<String> characterList;

    public GameNode(String name) {
        super(name);
        this.playerList = new ArrayList<>();
        this.characterList = new ArrayList<>();
    }

    // TODO This naming kinda blows, doesn't it?
    @Override
    public void save() {
        NodeWriter writer = new NodeWriter(this);
        writer.save();
    }

    public final void savePrep(String n, ArrayList<String> pn, ArrayList<String> cn) {
        changeName(n);
        super.path = String.valueOf(Paths.get(
                Defaults.getGamesPathAsString(),
                super.name + ".json"
        ));
        changePlayerNames(pn);
        changeCharacters(cn);
        save();
    }

    private void changePlayerNames(List<String> list) {
            this.playerList = new ArrayList<>(list);
    }

    private void changeCharacters(List<String> list) {
        this.characterList = new ArrayList<>(list);
    }

    public void addPlayer(String playerName) {
        this.playerList.add(playerName);
    }

    // Delete itself from the disk
    @Override
    public void delete() {
//        GameDeleter deleter = new GameDeleter(super.getPath());
//        deleter.delete();
        NodeDeleter deleter = new NodeDeleter(super.getPath());
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
        return super.getName();
    }

    public String getPath() {
        return super.getPath();
    }

    public void setPath(String path) {
        super.setPath(path);
    }

    public String getPlayerNamesAsString() {
        StringBuilder builder = new StringBuilder();
        // I think this is redundant bc Java checks for us
        if (playerList.isEmpty() || playerList == null) {
            return "";
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
