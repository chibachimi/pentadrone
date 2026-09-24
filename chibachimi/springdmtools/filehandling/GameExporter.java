package com.chibachimi.springdmtools.filehandling;

import com.chibachimi.springdmtools.createdfiles.Defaults;
import com.chibachimi.springdmtools.gamedata.GameNode;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

// TODO Can change this to handle multiple or all game files
public class GameExporter {

    Gson gson;
    GameNode game;

    public GameExporter(GameNode game) {
        this.game = game;
        this.gson = new Gson();
    }

    // TODO Test
    public void exportGame() {
        String downloadPath = String.valueOf(
                Paths.get(
                    Defaults.getDownloadsAsString(),
                        this.game.getName(),
                ".json"
        ));

        File file = new File(downloadPath);
        String gameJson = gson.toJson(game);

        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(gameJson);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
