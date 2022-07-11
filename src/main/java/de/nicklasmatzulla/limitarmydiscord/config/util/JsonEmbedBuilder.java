package de.nicklasmatzulla.limitarmydiscord.config.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import de.nicklasmatzulla.limitarmydiscord.config.entries.SettingsConfiguration;
import de.nicklasmatzulla.limitarmydiscord.discord.DiscordBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class JsonEmbedBuilder {

    /**
     * Creates an embed from a {@link InputStream}.
     *
     * @param source json as {@link InputStream}
     * @return {@link EmbedBuilder}
     */
    public static EmbedBuilder embedFromInputStream(@NotNull final InputStream source) {
        final EmbedBuilder embed = new EmbedBuilder();
        final JsonObject json = JsonParser.parseReader(new InputStreamReader(source)).getAsJsonObject();
        if (json.has("color")) {
            final JsonPrimitive colorObj = json.get("color").getAsJsonPrimitive();
            final Color color = Color.decode(colorObj.getAsString());
            embed.setColor(color);
        }
        if (json.has("thumbnail")) {
            final JsonPrimitive thumbnailObj = json.get("thumbnail").getAsJsonPrimitive();
            embed.setThumbnail(replaceVariables(thumbnailObj.getAsString()));
        }
        if (json.has("title")) {
            final JsonObject titleObj = json.get("title").getAsJsonObject();
            if (titleObj.has("text") && titleObj.has("url")) {
                final JsonPrimitive textObj = titleObj.get("text").getAsJsonPrimitive();
                final JsonPrimitive urlObj = titleObj.get("url").getAsJsonPrimitive();
                embed.setTitle(replaceVariables(textObj.getAsString()), replaceVariables(urlObj.getAsString()));
            } else if (titleObj.has("text")) {
                final JsonPrimitive textObj = titleObj.get("text").getAsJsonPrimitive();
                embed.setTitle(replaceVariables(textObj.getAsString()));
            }
        }
        if (json.has("author")) {
            final JsonObject authorObj = json.get("author").getAsJsonObject();
            if (authorObj.has("text") && authorObj.has("url") && authorObj.has("icon_url")) {
                final JsonPrimitive textObj = authorObj.get("text").getAsJsonPrimitive();
                final JsonPrimitive urlObj = authorObj.get("url").getAsJsonPrimitive();
                final JsonPrimitive iconUrlObj = authorObj.get("icon_url").getAsJsonPrimitive();
                embed.setAuthor(replaceVariables(textObj.getAsString()), replaceVariables(urlObj.getAsString()), replaceVariables(iconUrlObj.getAsString()));
            } else if (authorObj.has("text") && authorObj.has("icon_url")) {
                final JsonPrimitive textObj = authorObj.get("text").getAsJsonPrimitive();
                final JsonPrimitive urlObj = authorObj.get("url").getAsJsonPrimitive();
                embed.setAuthor(replaceVariables(textObj.getAsString()), replaceVariables(urlObj.getAsString()));
            } else if (authorObj.has("text")) {
                final JsonPrimitive textObj = authorObj.get("text").getAsJsonPrimitive();
                embed.setAuthor(replaceVariables(textObj.getAsString()));
            }
        }
        if (json.has("description")) {
            final JsonPrimitive descriptionObj = json.get("description").getAsJsonPrimitive();
            embed.setDescription(replaceVariables(descriptionObj.getAsString()));
        }
        if (json.has("fields")) {
            final JsonArray fieldsObj = json.get("fields").getAsJsonArray();
            fieldsObj.forEach(element -> {
                final JsonObject fieldObj = element.getAsJsonObject();
                if (fieldObj.has("title") && fieldObj.has("text") && fieldObj.has("inline")) {
                    final JsonPrimitive titleObj = fieldObj.get("title").getAsJsonPrimitive();
                    final JsonPrimitive textObj = fieldObj.get("text").getAsJsonPrimitive();
                    final JsonPrimitive inlineObj = fieldObj.get("inline").getAsJsonPrimitive();
                    embed.addField(replaceVariables(titleObj.getAsString()), replaceVariables(textObj.getAsString()), inlineObj.getAsBoolean());
                }
            });
        }
        if (json.has("image_url")) {
            final JsonPrimitive imageUrlObj = json.get("image_url").getAsJsonPrimitive();
            embed.setImage(replaceVariables(imageUrlObj.getAsString()));
        }
        if (json.has("footer")) {
            final JsonObject footerObj = json.get("footer").getAsJsonObject();
            if (footerObj.has("text") && footerObj.has("icon_url")) {
                final JsonPrimitive textObj = footerObj.get("text").getAsJsonPrimitive();
                final JsonPrimitive iconUrlObj = footerObj.get("icon_url").getAsJsonPrimitive();
                embed.setFooter(replaceVariables(textObj.getAsString()), replaceVariables(iconUrlObj.getAsString()));
            } else if (footerObj.has("icon_url")) {
                final JsonPrimitive textObj = footerObj.get("text").getAsJsonPrimitive();
                embed.setFooter(replaceVariables(textObj.getAsString()));
            }
            if (footerObj.has("timestamp")) {
                final JsonPrimitive timestampObj = footerObj.get("timestamp").getAsJsonPrimitive();
                if (timestampObj.getAsString().equalsIgnoreCase("<now>")) {
                    embed.setTimestamp(LocalDateTime.now());
                } else {
                    try {
                        final ZonedDateTime zonedDateTime = LocalDateTime.parse(
                                timestampObj.getAsString(),
                                DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
                        ).atZone(ZoneId.of("Europe/Berlin"));
                        embed.setTimestamp(zonedDateTime);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return embed;
    }

    /**
     * Replace all common variables with content
     *
     * @param message message that content should be replaced
     * @return formatted message
     */
    @SuppressWarnings("ConstantConditions")
    private static @NotNull String replaceVariables(@NotNull String message) {
        final JDA jda = DiscordBot.getInstance().getJDA();
        if (message.contains("<botAvatar>"))
            message = message.replace("<botAvatar>", jda.getSelfUser().getAvatarUrl());
        if (message.contains("<infoDot>"))
            message = message.replace("<infoDot>", SettingsConfiguration.getInfoDotEmoji().getAsMention());
        message = message.replace("\\n", "\n");
        return message;
    }

    /**
     * Creates an embed from a {@link File}.
     *
     * @param file json stored in a {@link File}
     * @return {@link EmbedBuilder}
     */
    public static EmbedBuilder embedFromFile(@NotNull final File file) {
        try {
            final InputStream fileInputStream = new FileInputStream(file);
            return embedFromInputStream(fileInputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create an embed with header and footer
     *
     * @param channel text channel in which the embed should be posted
     * @param builder predefined {@link EmbedBuilder} for message content
     * @param headerText text that should be displayed on the header banner
     */
    @SuppressWarnings("ConstantConditions")
    public static void createFormalEmbed(@NotNull final TextChannel channel, @NotNull final EmbedBuilder builder, @NotNull final String headerText) {
        final InputStream headerEmbedInputStream = JsonEmbedBuilder.class.getClassLoader().getResourceAsStream("configurations/embed/templates/header.json");
        final InputStream footerEmbedInputStream = JsonEmbedBuilder.class.getClassLoader().getResourceAsStream("configurations/embed/templates/footer.json");
        final InputStream headerImageInputStream = writeHeader(headerText);
        final InputStream footerImageInputStream = JsonEmbedBuilder.class.getClassLoader().getResourceAsStream("configurations/embed/templates/footer.png");
        final EmbedBuilder header = embedFromInputStream(headerEmbedInputStream);
        final EmbedBuilder footer = embedFromInputStream(footerEmbedInputStream);
        channel.sendMessage(" ")
                .setEmbeds(header.build(), builder.build(), footer.build())
                .addFile(footerImageInputStream, "footer.png")
                .addFile(headerImageInputStream, "header.png")
                .queue();
    }

    /**
     * Creates a banner with custom text
     *
     * @param text text that should be displayed on the banner
     * @return {@link InputStream} of graphic
     */
    @SuppressWarnings("ConstantConditions")
    private static InputStream writeHeader(@NotNull final String text) {
        try {
            final InputStream headerInputStream = JsonEmbedBuilder.class.getClassLoader().getResourceAsStream("configurations/embed/templates/header.png");
            final BufferedImage image = ImageIO.read(headerInputStream);
            final Font font = Font.createFont(Font.TRUETYPE_FONT, JsonEmbedBuilder.class.getClassLoader().getResourceAsStream("configurations/embed/templates/RubikMonoOne-Regular.ttf")).deriveFont(65f);
            final Graphics graphics = image.getGraphics();
            graphics.setFont(font);
            Graphics2D graphics2D = (Graphics2D) graphics.create();
            FontMetrics metrics = graphics2D.getFontMetrics();
            int x = (image.getWidth() - metrics.stringWidth(text)) / 2;
            int y = ((image.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
            graphics.drawString(text, x, y);
            final ByteArrayOutputStream headerImageOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", headerImageOutputStream);
            final byte[] headerImageBytes = headerImageOutputStream.toByteArray();
            return new ByteArrayInputStream(headerImageBytes);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }
}
