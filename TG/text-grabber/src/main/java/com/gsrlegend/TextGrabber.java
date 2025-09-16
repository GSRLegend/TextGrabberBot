package com.gsrlegend;

import java.util.EnumSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gsrlegend.config.BotConfig;
import com.gsrlegend.listeners.CommandListener;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class TextGrabber {
    private static final Logger logger = LoggerFactory.getLogger(TextGrabber.class);
    private static JDA jda;

    public static void main(String[] args) {
        String botToken = BotConfig.getBotToken();

        if (botToken == null || botToken.isEmpty()) {
            logger.error("Bot token is not found in config.properties. Please provide a valid token.");
            return;
        }

        try {
            jda = JDABuilder.createDefault(botToken)
                    .enableIntents(EnumSet.allOf(GatewayIntent.class))
                    .addEventListeners(new CommandListener())
                    .build();

            jda.awaitReady();
            logger.info("Bot is online and ready!");

            registerSlashCommands();
        } catch (Exception e) {
            logger.error("Error starting the bot: ", e);
        }
    }

    private static void registerSlashCommands() {
        if (jda == null) {
            logger.error("JDA instance is not initialized. Cannot register slash commands.");
            return;
        }

        logger.info("Registering Slash Commands...");
        jda.updateCommands()
            .addCommands(
                Commands.slash("ping", "Checks bots latency to Discord gateway"),
                Commands.slash("info", "Gives information about the bot"),
                Commands.slash("textgrab", "Returns text found in and image")
                        .addOption(OptionType.ATTACHMENT, "image", "Upload the image to scan", true)
            ).queue(success -> logger.info("Slash Commands registered successefully"), 
                        failure -> logger.error("Failed to register slash commands: ", failure));
    }
}
