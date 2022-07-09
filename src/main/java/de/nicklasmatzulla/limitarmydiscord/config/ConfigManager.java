package de.nicklasmatzulla.limitarmydiscord.config;

import de.nicklasmatzulla.limitarmydiscord.config.entries.ConfidentialConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    /**
     * Load all configurations
     */
    public static void loadConfigurations() {
        try {
            new ConfidentialConfiguration(new File("configurations/confidential.yml"), "configurations/confidential.yml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
