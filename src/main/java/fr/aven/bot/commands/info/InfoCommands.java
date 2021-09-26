package fr.aven.bot.commands.info;

import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public abstract class InfoCommands implements ICommand  {

    @Override
    public abstract void handle(List<String> args, CommandEvent event);

    @Override
    public abstract MessageEmbed.Field getHelp();

    @Override
    public Permission getPermission() { return Permission.USER; }

    @Override
    public abstract String getInvoke();

    @Override
    public Type getType() {
        return Type.INFO;
    }

    public abstract boolean haveEvent();

    public abstract void onEvent(GenericEvent event);
}
