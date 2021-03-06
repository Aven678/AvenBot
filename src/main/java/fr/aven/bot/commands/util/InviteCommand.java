package fr.aven.bot.commands.util;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class InviteCommand implements ICommand
{
    @Override
    public void handle(List<String> args, CommandEvent event) {
        String URL = "http://invite.justaven.xyz";

        event.getChannel().sendMessage(Main.getLanguage().getTextFor("invite.text", event.getGuild()) + URL).queue();
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
        return new MessageEmbed.Field(getDescription(), "Usage : `"+ Constants.PREFIX+ getInvoke()+"`", false);
    }

    @Override
    public String getInvoke() {
        return "invite";
    }

    @Override
    public String getDescription() {
        return "Shows the invite link";
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
        return Arrays.asList(net.dv8tion.jda.api.Permission.MESSAGE_EMBED_LINKS, net.dv8tion.jda.api.Permission.MESSAGE_EMBED_LINKS);
    }
}
