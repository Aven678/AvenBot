package fr.aven.bot.commands.fun;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class FakebanCommand implements ICommand
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        String text = Main.getDatabase().getTextJLB(event.getGuild().getId(),"ban");
        var textFinal = text.replaceAll("<guild>", event.getGuild().getName()).replaceAll("<member>", event.getMessage().getMentionedUsers().get(0).getAsTag()).replaceAll("<number>", String.valueOf(event.getGuild().getMembers().size()));

        if (event.getGuild().getSelfMember().hasPermission(net.dv8tion.jda.api.Permission.MESSAGE_MANAGE)) event.getMessage().delete().queue();
        event.getChannel().sendMessage(textFinal).queue();
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
        return new MessageEmbed.Field("", "Usage: `"+ Constants.PREFIX + getInvoke() +"`", false);
    }

    @Override
    public String getInvoke() {
        return "fakeban";
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
