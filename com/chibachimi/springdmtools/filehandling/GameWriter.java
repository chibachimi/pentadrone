package com.chibachimi.springdmtools.filehandling;

import com.chibachimi.springdmtools.gamedata.GameNode;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GameWriter {

    Gson gson = new Gson();
    GameNode game;

    public GameWriter saveGame(GameNode game) {
        this.game = game;
        File file = new File(this.game.getPath());
        if (!file.exists()) {
            createGame();
        }

        String gameJson = gson.toJson(game);

        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(gameJson);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    private void createGame() {
        try {
            Files.createFile(Path.of(game.getPath()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
