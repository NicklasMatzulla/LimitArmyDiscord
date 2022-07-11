package de.nicklasmatzulla.limitarmydiscord.config.entries;

import de.nicklasmatzulla.limitarmydiscord.config.util.ConfigBuilder;
import de.nicklasmatzulla.limitarmydiscord.discord.DiscordBot;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.sharding.ShardManager;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class SettingsConfiguration extends ConfigBuilder {
    private static SettingsConfiguration instance;

    /**
     * Create a yaml configuration or copies folder elements
     *
     * @param configurationFile file where the configuration should be saved at
     * @param resourcesPath     classpath location, where the source file is saved at
     */
    public SettingsConfiguration(@NotNull File configurationFile, @NotNull String resourcesPath) throws IOException {
        super(configurationFile, resourcesPath);
        SettingsConfiguration.instance = this;
    }

    /**
     * Get the blueDot emoji
     *
     * @return {@link Emoji}
     */
    @SuppressWarnings("ConstantConditions")
    public static Emoji getInfoDotEmoji() {
        final ShardManager shardManager = DiscordBot.getInstance().shardManager;
        final Emote emote = shardManager.getEmoteById(SettingsConfiguration.instance.getConfig().getLong("emojis.infoDot"));
        return Emoji.fromEmote(emote);
    }
}
