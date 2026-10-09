package com.chibachimi.springdmtools.logic;

import jakarta.annotation.Nullable;

import java.util.Objects;

public abstract class NodeItem {

    public String name;
    public String path;

    // TODO See if this is even useful
    public NodeItem(@Nullable String n) {
        this.name = Objects.requireNonNullElse(n, "");
        this.path = "";
    }

    // Prepares the NodeItem to be manipulated. Does not actually save or delete!
    // In these methods we call the NodeWriter class
    public abstract void save();

    public abstract void delete();

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return this.path;
    }
}
