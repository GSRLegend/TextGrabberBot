package com.gsrlegend.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PingCommand implements Command {
    

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Checks the bots latency to Discords gateway";
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event) {
        long ping = event.getJDA().getGatewayPing();
        event.replyFormat("Pong! Gateway Ping: %dms", ping).queue();
    }
}
