package com.chibachimi.springdmtools.createdfiles;

import org.springframework.context.annotation.Bean;

// TODO Could also probably be reworked later
public class OsChecker {

    public OsChecker() {

    }

    @Bean
    public Os getOs() {
        var checkingOs = System.getProperty("os.name").toLowerCase();
        Os matchedOs = Os.UNKNOWN;

        if (checkingOs.contains("win")) matchedOs = Os.WINDOWS;
        if (checkingOs.contains("mac")) matchedOs = Os.MACOS;
        if (checkingOs.contains("nix") | checkingOs.contains("nux")) matchedOs = Os.LINUX;

        return matchedOs;
    }
}
