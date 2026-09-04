package com.chibachimi.springdmtools;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class BrowserLauncher implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${server.port}")
    int port;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        try {
            Desktop.getDesktop().browse(new URI(loadRandomPort()));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private String loadRandomPort() {
        return "http://localhost:" + port;
    }
}
