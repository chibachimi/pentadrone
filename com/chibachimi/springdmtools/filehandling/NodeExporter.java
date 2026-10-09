package com.chibachimi.springdmtools.filehandling;

import com.chibachimi.springdmtools.createdfiles.Defaults;
import com.google.gson.Gson;
import com.vaadin.frontendtools.internal.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

// TODO Add other options, but for now this is good.
public class NodeExporter {
    Gson gson;

    public NodeExporter() {
        this.gson = new Gson();
    }

    public void exportAll() {
        File main = new File(Defaults.getHeadPathAsString());
        File holder = contentHolder();

        try {
            FileUtils.copyDirectory(main, holder, null, true);

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
        System.out.println("Data copied");
    }

    private File contentHolder() {
        String path = String.valueOf(Paths.get(Defaults.getDownloadsAsString(), "pentadrone-exported"));
        return new File(path);
    }
}
