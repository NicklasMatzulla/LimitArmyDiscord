package de.nicklasmatzulla.limitarmydiscord.config.util;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ConfigBuilder {
    private final YamlFile config;

    /**
     * Create a yaml configuration
     *
     * @param configurationFile file, where the configuration should be saved at
     * @param resourcesPath classpath location, where the source file is saved at
     */
    @SuppressWarnings("ConstantConditions")
    public ConfigBuilder(@NotNull final File configurationFile, @NotNull final String resourcesPath) throws IOException {
        if (!configurationFile.exists()) {
            final InputStream resourceInputStream = getClass().getClassLoader().getResourceAsStream(resourcesPath);
            FileUtils.copyInputStreamToFile(resourceInputStream, configurationFile);
        }
        this.config = YamlFile.loadConfiguration(configurationFile, true);
    }

    /**
     * Get the configuration
     *
     * @return {@link YamlFile}
     */
    public YamlFile getConfig() {
        return config;
    }
}
