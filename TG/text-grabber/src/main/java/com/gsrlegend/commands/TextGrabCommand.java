package com.gsrlegend.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.io.File;
import java.nio.file.Path;

import net.dv8tion.jda.api.entities.Message;
import net.sourceforge.tess4j.*;

import com.gsrlegend.config.*;

public class TextGrabCommand implements Command {
    
    @Override 
    public String getName() {
        return "textgrab";
    }

    @Override
    public String getDescription() {
        return "Returns text found in an image";
    }

    @Override
    public void executeSlash(SlashCommandInteractionEvent event) {
        Message.Attachment attachment = event.getOption("image").getAsAttachment();

        if (attachment == null || !attachment.isImage()) {
            event.reply("Please upload a valid image file.").setEphemeral(true);
            return;
        }
        
        event.deferReply().queue();

        attachment.getProxy().downloadToPath(Path.of("temp.png")).thenAccept(path -> {
            try {
                ITesseract tesseract = new Tesseract();

                String tessdataPath = TessdataUtil.extractTessdata();

                tesseract.setDatapath(tessdataPath);
                tesseract.setLanguage("eng");

                String result = tesseract.doOCR(path.toFile());

                if (result.trim().isEmpty()) {
                    event.getHook().sendMessage("No readable text found in image.").queue();
                } else {
                    event.getHook().sendMessage("Extracted text:\n```\n" + result.trim() + "\n```").queue();
                }

                path.toFile().delete();
            } catch (Exception e) {
                event.getHook().sendMessage("OCR failed: " + e.getMessage()).queue();
            }
        });
    }

}
