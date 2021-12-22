package fr.aven.bot.commands.modo;

import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class ModoCommands implements ICommand {

    protected Logger MODOLOGGER = LoggerFactory.getLogger("Modo_Command");

    @Override
    public abstract void handle(List<String> args, CommandEvent event);

    @Override
    public abstract MessageEmbed.Field getHelp();

    @Override
    public abstract String getInvoke();

    @Override
    public abstract String getDescription();

    @Override
    public Type getType() {
        return Type.MODO;
    }

    public abstract boolean haveEvent();

    public abstract void onEvent(GenericEvent event);

    @Override
    public Permission getPermission()
    {
        return Permission.MODO;
    }
}
