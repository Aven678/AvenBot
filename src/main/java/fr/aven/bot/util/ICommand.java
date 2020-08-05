package fr.aven.bot.util;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Collection;
import java.util.List;

public interface ICommand
{
    void handle(List<String> args, GuildMessageReceivedEvent event);

    Type getType();
    Permission getPermission();
    MessageEmbed.Field getHelp();
    String getInvoke();
    boolean haveEvent();
    void onEvent(GenericEvent event);
    Collection<net.dv8tion.jda.api.Permission> requiredDiscordPermission();

    enum Type
    {
        ADMIN,
        MODO,
        MUSIC,
        INFO,
        UTIL,
        FUN,
        SEARCH,
        INFO_SUB
    }

    enum Permission
    {
        MODO,
        DJ,
        ADMIN,
        OWNER,
        USER
    }
}
