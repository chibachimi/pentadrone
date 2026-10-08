package com.chibachimi.springdmtools.experimental;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NodeWriter {

    Gson gson = new Gson();
    NodeItem node;

    public NodeWriter(NodeItem node) {
        this.node = node;
    }

    private void createItem() {
        try {
            Files.createFile(Path.of(node.getPath()));

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    public void save() {
        File file = new File(node.getPath());
        System.out.println(file.getAbsolutePath());
        if (!file.exists()) {
            System.out.println("File created");
            createItem();
        }

        String nodeJson = this.gson.toJson(this.node);
        System.out.println(nodeJson);

        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(nodeJson);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
