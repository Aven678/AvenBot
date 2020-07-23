package fr.aven.bot.commands.fun;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class DogCommand implements ICommand
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage(Main.getkSoft().getDogPicture()).queue();
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
        return new MessageEmbed.Field("Sends a beautiful picture of dogs", "Usage: `" + Constants.PREFIX + getInvoke() + "`", false);
    }

    @Override
    public String getInvoke() {
        return "dog";
    }

    @Override
    public boolean haveEvent() {
        return false;
    }

    @Override
    public void onEvent(GenericEvent event) {

    }
}
