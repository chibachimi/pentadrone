package com.chibachimi.springdmtools.filehandling;

import java.io.File;

// Wanted this to be its own class so that GameNode does not have to touch files itself
public class GameDeleter {

    File file;

    public GameDeleter(String path) {
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
