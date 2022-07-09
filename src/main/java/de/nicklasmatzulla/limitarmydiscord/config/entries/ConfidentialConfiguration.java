package de.nicklasmatzulla.limitarmydiscord.config.entries;

import de.nicklasmatzulla.limitarmydiscord.config.util.ConfigBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class ConfidentialConfiguration extends ConfigBuilder {
    private static ConfidentialConfiguration instance;

    /**
     * Create a yaml configuration
     *
     * @param configurationFile file, where the configuration should be saved at
     * @param resourcesPath     classpath location, where the source file is saved at
     */
    public ConfidentialConfiguration(@NotNull File configurationFile, @NotNull String resourcesPath) throws IOException {
        super(configurationFile, resourcesPath);
        ConfidentialConfiguration.instance = this;
    }

    /**
     * Get the bot token from the configuration
     *
     * @return {@link String}
     */
    public static String getBotToken() {
        return ConfidentialConfiguration.instance.getConfig().getString("discord.token");
    }
}
