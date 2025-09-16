package com.gsrlegend.listeners;

import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gsrlegend.commands.*;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(CommandListener.class);
    private final Map<String, Command> commands = new HashMap<>();

    public CommandListener() {
        commands.put("ping", new PingCommand());
        commands.put("info", new InfoCommand());
        commands.put("textgrab", new TextGrabCommand());
        logger.info("Registered {} commands.", commands.size());
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        logger.info("JDA is ready! Logged in as {}#{}", 
                    event.getJDA().getSelfUser().getName(),
                    event.getJDA().getSelfUser().getDiscriminator());
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        Command command = commands.get(commandName);

        if (command != null) {
            logger.debug("Executing slash comand: {} from user: {}", commandName, event.getUser());
            command.executeSlash(event);
        } else {
            logger.warn("Unkown Slash Command: {} from user: {}", commandName, event.getUser());
            event.reply("Unknown command!").setEphemeral(true).queue();
        }
    }
}
