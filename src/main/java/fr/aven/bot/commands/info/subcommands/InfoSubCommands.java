package fr.aven.bot.commands.info.subcommands;

import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public abstract class InfoSubCommands implements ICommand {

    @Override
    public abstract void handle(List<String> args, GuildMessageReceivedEvent event);

    @Override
    public abstract MessageEmbed.Field getHelp();

    @Override
    public abstract String getInvoke();

    @Override
    public abstract boolean haveEvent();

    @Override
    public abstract void onEvent(GenericEvent event);

    @Override
    public Type getType() {
        return Type.INFO_SUB;
    }

    @Override
    public Permission getPermission()
    {
        return Permission.USER;
    }
}
