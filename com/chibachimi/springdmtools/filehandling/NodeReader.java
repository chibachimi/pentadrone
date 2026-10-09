package com.chibachimi.springdmtools.filehandling;

import com.chibachimi.springdmtools.createdfiles.Defaults;
import com.chibachimi.springdmtools.experimental.TableNode;
import com.chibachimi.springdmtools.gamedata.GameNode;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// This class reads Nodes from files on the user's computer.
public class NodeReader {

    private final Gson gson;
    private NodeType currentType;
    private List<File> loadedFiles;

    // TODO This absolutely should merge loadFiles into the constructor
    public NodeReader() {
        gson = new Gson();
    }

    // Pass in a NodeType to specify which kinda of nodes we'll be getting
    public void loadFiles(NodeType nodeType) {
        // Get the NodeType
        this.currentType = nodeType;

        // Then we can load all the files based on which type was chosen.
        String currentPath = new String();
        switch (nodeType) {
            case GAME:
                currentPath = Defaults.getGamesPathAsString();
                break;
            case TABLE:
                System.out.println("TODO: Table folder");
                break;
            case null, default:
                System.out.println("Critical Error: Node type is invalid or does not exist.");
        }

        File folder = new File(currentPath);
        loadedFiles = Arrays.stream(Objects.requireNonNull(folder.listFiles())).toList();
    }

    // Methods to get certain kinds of Nodes
    public ArrayList<GameNode> getGamesList() {
        // Stop us shooting ourselves in the foot.
        if (!(currentType == NodeType.GAME)) {
            System.out.println("Error: Called method (getGamesList) and selected NodeType does not match!");
        }

        ArrayList<GameNode> games = new ArrayList<>();
        for (File file : loadedFiles) {
            try {

                GameNode game = gson.fromJson(Files.readString(file.toPath()), GameNode.class);
                games.add(game);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return games;
    }

    // TODO Finish this later
    public ArrayList<TableNode> getTablesList() {
        return new ArrayList<>();
    }
}
