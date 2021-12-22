package fr.aven.bot.commands.fun;

import fr.aven.bot.Constants;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SayCommand implements ICommand
{
    @Override
    public void handle(List<String> args, CommandEvent event) {
        if (args.isEmpty())
        {
            event.getChannel().sendMessageEmbeds(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        String request = event.message().getContentRaw().replaceFirst(Constants.PREFIX + getInvoke(), "");

        if (event.getGuild().getSelfMember().hasPermission(event.getChannel(), net.dv8tion.jda.api.Permission.MESSAGE_MANAGE)) event.message().delete().queue();
        event.getChannel().sendMessageEmbeds(new EmbedBuilder().setDescription(request).setColor(event.getMember().getColor()).build()).queue();

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
        return new MessageEmbed.Field(getDescription(), "Example: `" + Constants.PREFIX + getInvoke() + " i love AvenBot`", false);
    }

    @Override
    public String getInvoke() {
        return "say";
    }

    @Override
    public String getDescription() {
        return "Repeats your text";
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
