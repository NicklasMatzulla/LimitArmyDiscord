package de.nicklasmatzulla.limitarmydiscord;

import de.nicklasmatzulla.limitarmydiscord.config.ConfigManager;
import de.nicklasmatzulla.limitarmydiscord.discord.DiscordBot;

public class LimitArmyDiscord {

    /**
     * Create the initial instance of the discord bot
     */
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static void main(String[] args) {
        new LimitArmyDiscord();
    }

    /**
     * Initialize all functions and the discord bot itself
     */
    public LimitArmyDiscord() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> DiscordBot.getInstance().shutdown()));
        ConfigManager.loadConfigurations();
        new DiscordBot();
    }
}
