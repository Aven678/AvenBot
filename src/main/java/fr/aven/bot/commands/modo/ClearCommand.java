package fr.aven.bot.commands.modo;

import fr.aven.bot.Constants;
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.utils.TimeUtil;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClearCommand implements ICommand
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        if (args.isEmpty())
        {
            event.getChannel().sendMessage(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }
        try {
            int count = Integer.parseInt(args.get(0));

            event.getChannel().getHistory().retrievePast(count-1).queue(historyMessage -> event
                    .getChannel()
                    .deleteMessages(getMessageCanDeleted(historyMessage))
                    .queue(success -> event.getChannel().sendMessage("\uD83D\uDDD1️ "+getMessageCanDeleted(historyMessage).size()+" successfully deleted!").queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS))));
        } catch (NumberFormatException nfe)
        {
            event.getChannel().sendMessage(new EmbedBuilder().addField(getHelp()).build()).queue();
        }
    }

    public List<Message> getMessageCanDeleted(List<Message> messages)
    {
        List<Message> newMessages = new ArrayList<>();
        for (Message message : messages) {
            Duration dur = Duration.between(message.getTimeCreated(), OffsetDateTime.now());
            if (!(dur.toDays() > 14))
                newMessages.add(message);
        }

        return newMessages;
    }

    @Override
    public Type getType() {
        return Type.MODO;
    }

    @Override
    public Permission getPermission() {
        return Permission.MODO;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Clears messages", "Usage: `"+ Constants.PREFIX + getInvoke() +"<number>`", false);
    }

    @Override
    public String getInvoke() {
        return "clear";
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
        return Arrays.asList(net.dv8tion.jda.api.Permission.MESSAGE_MANAGE);
    }
}
