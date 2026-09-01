package com.chibachimi.springdmtools.createdfiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// TODO Honestly most of this could be rewritten later
public class Defaults {

    private final Os os;
    private final String name = "dmtools";
    private static Path headPath;
    private static Path gamesDir;

    @Autowired
    ApplicationContext context;

    public Defaults(OsChecker checker) {
        os = checker.getOs();

        headPath = defaultFileStorage();
        gamesDir = Paths.get(String.valueOf(headPath), "games");

        if (headPath == null) {
            System.err.println("Fatal error finding defaultFileStorage on your operating system: " + os);
            SpringApplication.exit(context);
            // Not sure if this is necessary, but it just feels right.
            System.exit(1);
        }
        createNewDir(headPath);
        createNewDir(gamesDir);
    }

    private Path defaultFileStorage() {
        Path path = null;

        switch (os) {
            case LINUX ->
                    path = Paths.get( System.getProperty("user.home"), ("." + name));
            case MACOS ->
                    path = Paths.get( System.getProperty("user.home"), "Library", "Application Support", name);
            case WINDOWS ->
                    path = Paths.get(System.getenv("APPDATA"), name);
            case UNKNOWN ->
                    // If unknown, attempt to make folder in user home?
                    path = Paths.get(System.getProperty("user.home"), name);
        }

        return path;
    }

    // Saves us from more try/catch blocks if we have to call this in other places
    private void createNewDir(Path path) {
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String getGamesPathAsString() {
        return String.valueOf(gamesDir);
    }
}
