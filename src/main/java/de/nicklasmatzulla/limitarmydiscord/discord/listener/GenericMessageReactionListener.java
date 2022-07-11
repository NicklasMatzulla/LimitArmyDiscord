package de.nicklasmatzulla.limitarmydiscord.discord.listener;

import de.nicklasmatzulla.limitarmydiscord.config.util.JsonEmbedBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class GenericMessageReactionListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        final EmbedBuilder embed = JsonEmbedBuilder.embedFromFile(new File("configurations/embed/channels/welcome/WelcomeMessage.json"));
        JsonEmbedBuilder.createFormalEmbed(event.getTextChannel(), embed, "Willkommen");
    }
}
