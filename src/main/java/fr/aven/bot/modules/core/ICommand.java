package fr.aven.bot.modules.core;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public interface ICommand
{
    void handle(List<String> args, CommandEvent event);

    Type getType();
    Permission getPermission();
    MessageEmbed.Field getHelp();
    String getInvoke();
    String getDescription();
    boolean haveEvent();
    void onEvent(GenericEvent event);
    Collection<net.dv8tion.jda.api.Permission> requiredDiscordPermission();

    default Collection<String> getAlias() {
        return Arrays.asList();
    }

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
