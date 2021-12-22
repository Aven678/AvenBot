package fr.aven.bot.commands.modo;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.modules.core.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class KickCommand extends ModoCommands {

    @Override
    public void handle(List<String> args, CommandEvent event)
    {
        TextChannel channel = event.getChannel();
        Message message = event.message();

        if (!message.getGuild().getSelfMember().hasPermission(net.dv8tion.jda.api.Permission.KICK_MEMBERS))
        {
            channel.sendMessageEmbeds(new EmbedBuilder()
                    .setTitle(Main.getLanguage().getTextFor("error", event.getGuild()))
                    .setDescription(Main.getLanguage().getTextFor("hasNotPermission", event.getGuild()))
                    .setColor(Color.RED)
                    .setFooter("Command executed by "+event.getAuthor().getAsTag())
                    .build()
            ).queue();
            return;
        }

        if (args.isEmpty())
        {
            channel.sendMessage(Main.getLanguage().getTextFor("argsNotFound", event.getGuild())).queue();
            return;
        }
        else if (message.getMentionedUsers().isEmpty())
        {
            channel.sendMessage(Main.getLanguage().getTextFor("kick.notMentionned", event.getGuild())).queue();

            return;
        }

        if (message.getMentionedUsers().get(0).getId().equalsIgnoreCase(message.getAuthor().getId()))
        {
            channel.sendMessage(new EmbedBuilder().setDescription("You can't kick yourself!").setColor(Color.RED).build()).queue();
            return;
        }

        if (message.toString().length() > 2)
        {

            String reason = StringUtils.join(args, " ").replaceFirst(message.getMentionedUsers().get(0).getAsTag(), "");

            Main.getDatabase().addKick(message.getMentionedUsers().get(0).getId(), event.getGuild().getId(), message.getAuthor().getId(), message.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), reason);
            event.getGuild().kick(message.getMentionedMembers().get(0), reason).queue();

            channel.sendMessage(Main.getLanguage().getTextFor("kick.confirm", event.getGuild())).queue();

            MODOLOGGER.info(event.getAuthor().getName() + " kicked " + message.getMentionedMembers().get(0).getEffectiveName() + " for: " + reason);

        }
    }

    @Override
    public MessageEmbed.Field getHelp()
    {
        return new MessageEmbed.Field(getDescription(), "Usage: `" + Constants.PREFIX + getInvoke() + " <@user> [reason]`", false);
    }

    @Override
    public boolean haveEvent() {
        return false;
    }

    @Override
    public void onEvent(GenericEvent event) {}

    @Override
    public String getInvoke()
    {
        return "kick";
    }

    @Override
    public String getDescription() {
        return "Kick a member";
    }

    @Override
    public Collection<net.dv8tion.jda.api.Permission> requiredDiscordPermission() {
        return Arrays.asList(net.dv8tion.jda.api.Permission.KICK_MEMBERS, net.dv8tion.jda.api.Permission.MESSAGE_EMBED_LINKS);
    }
}
