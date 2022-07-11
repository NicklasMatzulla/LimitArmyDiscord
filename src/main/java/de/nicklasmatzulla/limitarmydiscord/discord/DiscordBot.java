package de.nicklasmatzulla.limitarmydiscord.discord;

import de.nicklasmatzulla.limitarmydiscord.config.entries.ConfidentialConfiguration;
import de.nicklasmatzulla.limitarmydiscord.discord.listener.GenericMessageReactionListener;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.util.Arrays;

public class DiscordBot {
    private static DiscordBot instance;
    private ShardManager shardManager;

    /**
     * Initialize a new discord bot within all of his features
     */
    public DiscordBot() {
        start();
        DiscordBot.instance = this;
    }

    /**
     * Start an instance of the discord bot and register all functions
     */
    public void start() {
        try {
            final String botToken = ConfidentialConfiguration.getBotToken();
            this.shardManager = DefaultShardManagerBuilder.createDefault(botToken)
                    .enableIntents(Arrays.asList(GatewayIntent.values()))
                    .enableCache(Arrays.asList(CacheFlag.values()))
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .setActivity(Activity.watching("der LimitArmy zu..."))
                    .setAutoReconnect(true)
                    .setIdle(true)
                    .addEventListeners(new GenericMessageReactionListener())
                    .build();
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Stops the current running instance of the discord bot
     */
    public void shutdown() {
        this.shardManager.shutdown();
    }

    public static DiscordBot getInstance() {
        return instance;
    }
}
