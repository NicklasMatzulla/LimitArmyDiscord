package de.nicklasmatzulla.limitarmydiscord.config;

import de.nicklasmatzulla.limitarmydiscord.config.entries.ConfidentialConfiguration;
import de.nicklasmatzulla.limitarmydiscord.config.entries.EmbedConfigurations;
import de.nicklasmatzulla.limitarmydiscord.config.entries.SettingsConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    /**
     * Load all configurations
     */
    public static void loadConfigurations() {
        try {
            new ConfidentialConfiguration(new File("configurations/confidential.yml"), "configurations/confidential.yml");
            new SettingsConfiguration(new File("configurations/settings.yml"), "configurations/settings.yml");
            new EmbedConfigurations();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
