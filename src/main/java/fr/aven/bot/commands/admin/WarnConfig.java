package fr.aven.bot.commands.admin;

import fr.aven.bot.Constants;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class WarnConfig implements ICommand
{
    @Override
    public void handle(List<String> args, CommandEvent event) {

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
        return new MessageEmbed.Field("Config automod warns.", "Usage : `"+ Constants.PREFIX+ getInvoke()+" <limit/type/disable>`"
                +"\nLimit = Automod enabled when the user has (limit) warns." +
                "\nType: kick/ban", false);
    }

    @Override
    public String getInvoke() {
        return "warnconfig";
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
