package de.nicklasmatzulla.limitarmydiscord.config.entries;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class EmbedConfigurations {

    /**
     * Saves all configurations files on device
     */
    public EmbedConfigurations() {
        copyFile("configurations/embed/channels/welcome/WelcomeMessage.json");
    }

    /**
     * Copy a configuration file from classpath to the device
     *
     * @param resourcesDirectory classpath location of configuration
     */
    @SuppressWarnings("ConstantConditions")
    private void copyFile(@NotNull final String resourcesDirectory) {
        final File configuration = new File(resourcesDirectory);
        try {
            if (!configuration.exists()) {
                final InputStream resourcesInputStream = getClass().getClassLoader().getResourceAsStream(resourcesDirectory);
                FileUtils.copyInputStreamToFile(resourcesInputStream, configuration);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
