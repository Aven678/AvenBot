package fr.aven.bot.commands.fun;

import fr.aven.bot.Constants;
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.*;

public class RollCommand implements ICommand
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        Random random = new Random();
        var number = random.nextInt(100);

        try {
            if (args.size() > 0)
                number = random.nextInt(Integer.parseInt(args.get(0)));
        } catch (NumberFormatException nfe) {}

        event.getChannel().sendMessage("Roll: "+number).queue();
    }

    @Override
    public Type getType() {
        return Type.FUN;
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Generates a random number (limit is default 100).", "Usage: `"+ Constants.PREFIX + getInvoke() + " [limit]`", false);
    }

    @Override
    public String getInvoke() {
        return "roll";
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
        return Collections.singletonList(net.dv8tion.jda.api.Permission.MESSAGE_WRITE);
    }
}
