package fr.aven.bot.commands.music;

import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class MusicCommands implements ICommand {

    protected Logger MUSICLOGGER = LoggerFactory.getLogger("Music_Command");

    @Override
    public abstract void handle(List<String> args, GuildMessageReceivedEvent event);

    @Override
    public abstract MessageEmbed.Field getHelp();

    @Override
    public abstract String getInvoke();

    @Override
    public abstract Permission getPermission();

    @Override
    public Type getType() {
        return Type.MUSIC;
    }

    public boolean haveEvent() {
        return false;
    }

    public void onEvent(GenericEvent event) {}
}
