package fr.aven.bot.commands.music;

import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class LyricsCommand implements ICommand
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

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
    public boolean haveEvent() {
        return false;
    }

    @Override
    public void onEvent(GenericEvent event) {

    }
}
