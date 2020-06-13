package fr.aven.bot.commands.admin;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class ClearConfigCommand implements ICommand
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if (Main.getDatabase().resetConfig(event.getGuild()))
            event.getChannel().sendMessage(new EmbedBuilder().setTitle(Main.getDatabase().getTextFor("success", event.getGuild()))
                    .setDescription(Main.getDatabase().getTextFor("cconfig.success", event.getGuild()))
                    .setColor(Color.GREEN)
                    .setFooter("AvenBot by Aven#1000")
                    .build()).queue();
        else
            event.getChannel().sendMessage(new EmbedBuilder().setTitle(Main.getDatabase().getTextFor("error", event.getGuild()), "https://avenbot.eu")
                    .setDescription(Main.getDatabase().getTextFor("errordesc", event.getGuild()))
            .setColor(Color.RED)
            .setFooter("AvenBot by Aven#1000").build()).queue();
    }

    @Override
    public Type getType() {
        return Type.ADMIN;
    }

    @Override
    public Permission getPermission() {
        return Permission.ADMIN;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Clears the bot config.", "Usage: `" + Constants.PREFIX + getInvoke() + "`", false);
    }

    @Override
    public String getInvoke() {
        return "clearconfig";
    }

    @Override
    public boolean haveEvent() {
        return false;
    }

    @Override
    public void onEvent(GenericEvent event) {

    }
}
