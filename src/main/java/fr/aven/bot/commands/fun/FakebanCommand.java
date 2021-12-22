package fr.aven.bot.commands.fun;

import fr.aven.bot.Constants;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class FakebanCommand implements ICommand
{
    @Override
    public void handle(List<String> args, CommandEvent event) {
        if (event.message().getMentionedUsers().size() > 0)
        {
            var textFinal = event.message().getMentionedUsers().get(0).getAsMention()+" has been banned...";
            event.getChannel().sendMessage(textFinal).queue();
        }

        if (event.getGuild().getSelfMember().hasPermission(net.dv8tion.jda.api.Permission.MESSAGE_MANAGE)) event.message().delete().queue();
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
        return new MessageEmbed.Field(getDescription(), "Usage: `"+ Constants.PREFIX + getInvoke() +" <@member>`", false);
    }

    @Override
    public String getInvoke() {
        return "fakeban";
    }

    @Override
    public String getDescription() {
        return "FakeBan a member";
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
