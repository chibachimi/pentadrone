package com.chibachimi.springdmtools.filehandling;

import java.io.File;

// TODO Largely copied from GameDeleter. Not sure if I like it though.
public class NodeDeleter {
    File file;

    public NodeDeleter(String path) {
        this.file = new File(path);
    }

    public void delete() {
        if (file.delete()) {
            System.out.println("File at: " + file.getAbsolutePath() + "was successfully deleted.");
        } else {
            System.err.println("Unable to remove file, or file does not exist.");
        }
    }
}
