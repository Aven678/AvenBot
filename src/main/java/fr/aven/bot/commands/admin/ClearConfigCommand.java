package fr.aven.bot.commands.admin;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ClearConfigCommand implements ICommand
{
    @Override
    public void handle(List<String> args, CommandEvent event) {
        if (Main.getDatabase().resetConfig(event.getGuild()))
            event.getChannel().sendMessageEmbeds(new EmbedBuilder().setTitle(Main.getLanguage().getTextFor("success", event.getGuild()))
                    .setDescription(Main.getLanguage().getTextFor("cconfig.success", event.getGuild()))
                    .setColor(Color.GREEN)
                    .setFooter("AvenBot by Aven#1000")
                    .build()).queue();
        else
            event.getChannel().sendMessageEmbeds(new EmbedBuilder().setTitle(Main.getLanguage().getTextFor("error", event.getGuild()), "https://avenbot.eu")
                    .setDescription(Main.getLanguage().getTextFor("errordesc", event.getGuild()))
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

    @Override
    public Collection<net.dv8tion.jda.api.Permission> requiredDiscordPermission() {
        return Arrays.asList(net.dv8tion.jda.api.Permission.MESSAGE_WRITE);
    }
}
