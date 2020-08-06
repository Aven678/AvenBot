package fr.aven.bot.commands.fun;

import fr.aven.bot.Constants;
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SayCommand implements ICommand
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if (args.isEmpty())
        {
            event.getChannel().sendMessage(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        String request = event.getMessage().getContentRaw().replaceFirst(Constants.PREFIX + getInvoke(), "");

        event.getChannel().sendMessage(new EmbedBuilder().setDescription(request).setColor(event.getMember().getColor()).build()).queue();

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
        return new MessageEmbed.Field("Repeats your text", "Example: `" + Constants.PREFIX + getInvoke() + " i love AvenBot`", false);
    }

    @Override
    public String getInvoke() {
        return "say";
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
        return Arrays.asList(net.dv8tion.jda.api.Permission.MESSAGE_WRITE, net.dv8tion.jda.api.Permission.MESSAGE_EMBED_LINKS);
    }
}
