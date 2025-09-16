package com.gsrlegend.commands;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class InfoCommand implements Command {
    

    @Override
    public String getName() {
        return "info";
    }

    @Override 
    public String getDescription() {
        return "Gives info about the bot";
    }

    @Override 
    public void executeSlash(SlashCommandInteractionEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Bot Information");
        embedBuilder.setDescription("This is a discord bot that takes in images and returns the text from in the image.");
        embedBuilder.setColor(new Color(148, 0, 211));
        embedBuilder.addField("Author", "GSRLegend", false);
        embedBuilder.addField("Language", "Java", true);
        embedBuilder.addField("Library", "JDA", true);

        MessageEmbed embed = embedBuilder.build();
        event.replyEmbeds(embed).queue();
    }
}
