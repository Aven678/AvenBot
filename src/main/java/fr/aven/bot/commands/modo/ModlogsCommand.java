package fr.aven.bot.commands.modo;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ModlogsCommand implements ICommand
{
    @Override
    public void handle(List<String> args, CommandEvent event) {
        if (event.message().getMentionedUsers().size() == 0)
        {
            event.getChannel().sendMessageEmbeds(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        EmbedBuilder builder = Main.getDatabase().getModLogs(event.message().getMentionedUsers().get(0), event.getGuild());

        event.getChannel().sendMessageEmbeds(builder.build()).queue();
    }

    @Override
    public Type getType() {
        return Type.MODO;
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Shows moderation logs for a user", "Usage: `" + Constants.PREFIX + getInvoke() + " <@user>`", false);
    }

    @Override
    public String getInvoke() {
        return "modlogs";
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
        return Arrays.asList(net.dv8tion.jda.api.Permission.MESSAGE_WRITE, net.dv8tion.jda.api.Permission.MESSAGE_MANAGE);
    }
}
