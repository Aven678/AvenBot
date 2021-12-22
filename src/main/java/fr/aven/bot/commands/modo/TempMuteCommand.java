package fr.aven.bot.commands.modo;

import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Collection;
import java.util.List;

public class TempMuteCommand implements ICommand
{
    @Override
    public void handle(List<String> args, CommandEvent event) {

    }

    @Override
    public Type getType() {
        return null;
    }

    @Override
    public Permission getPermission() {
        return null;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return null;
    }

    @Override
    public String getInvoke() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
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
        return null;
    }
}
