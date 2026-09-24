package com.chibachimi.springdmtools.filehandling;

import com.chibachimi.springdmtools.createdfiles.Defaults;
import com.chibachimi.springdmtools.gamedata.GameNode;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GameReader {

    Gson gson;
    String gamesPathAsString;
    List<GameNode> gamesList;

    public GameReader() {

        this.gson = new Gson();
        this.gamesPathAsString = Defaults.getGamesPathAsString();
    }

    public ArrayList<GameNode> getGamesList() {
        ArrayList<GameNode> gamesList = new ArrayList<>();

        var gameFiles = readGames();
        for (File file : gameFiles) {
            try {
                GameNode game = gson.fromJson(Files.readString(file.toPath()), GameNode.class);
                gamesList.add(game);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return gamesList;
    }

    // We might use this later
    public List<String> getGamesNames() {
        List<String> names = new ArrayList<>();
        for (GameNode game : gamesList) {
            names.add(game.getName());
        }
        return names;
    }

    private List<File> readGames() {
        File gamesFolder = new File(gamesPathAsString);

        return Arrays.stream(Objects.requireNonNull(gamesFolder.listFiles())).toList();
    }

}
