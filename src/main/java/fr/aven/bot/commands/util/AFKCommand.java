package fr.aven.bot.commands.util;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AFKCommand implements ICommand
{
    @Override
    public void handle(List<String> args, CommandEvent event) {
        var reason = "";

        if (args.size() > 0) reason = StringUtils.join(args, " ");
        Main.getDatabase().addAFK(event.getGuild(), event.getAuthor(), reason);
        event.getChannel().sendMessage("AFK enabled!").queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
    }

    @Override
    public Type getType() {
        return Type.UTIL;
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Put an AFK message when you are mentioned.","Usage: `"+ Constants.PREFIX + getInvoke() +" [reason]`", false);
    }

    @Override
    public String getInvoke() {
        return "afk";
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
