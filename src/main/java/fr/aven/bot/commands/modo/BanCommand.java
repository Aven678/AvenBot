package fr.aven.bot.commands.modo;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
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
import java.util.List;

public class BanCommand extends ModoCommands {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        Message message = event.getMessage();

        if (!message.getGuild().getSelfMember().hasPermission(net.dv8tion.jda.api.Permission.BAN_MEMBERS))
        {
            channel.sendMessage(new EmbedBuilder()
                    .setTitle(Main.getDatabase().getTextFor("error", event.getGuild()))
                    .setDescription(Main.getDatabase().getTextFor("hasNotPermission", event.getGuild()))
                    .setColor(Color.RED)
                    .setFooter("Command executed by "+event.getAuthor().getAsTag())
                    .build()
            ).queue();
            return;
        }

        if (args.isEmpty())
        {
            channel.sendMessage(Main.getDatabase().getTextFor("argsNotFound", event.getGuild())).queue();

            return;
        } else if (message.getMentionedMembers().isEmpty()) {

            channel.sendMessage(Main.getDatabase().getTextFor("ban.notMentionned", event.getGuild())).queue();

            return;
        }

        if (message.getMentionedUsers().get(0).getId().equalsIgnoreCase(message.getAuthor().getId()))
        {
            channel.sendMessage(new EmbedBuilder().setDescription("You can't ban yourself!").setColor(Color.RED).build()).queue();
            return;
        }

        if (message.toString().length() > 2) {

            String reason = StringUtils.join(args, " ").replaceFirst(message.getMentionedUsers().get(0).getAsTag(), "");

            Main.getDatabase().addBan(message.getMentionedUsers().get(0).getId(), event.getGuild().getId(), message.getAuthor().getId(), message.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), reason);
            event.getGuild().ban(message.getMentionedMembers().get(0), 0, reason).queue();

            channel.sendMessage(Main.getDatabase().getTextFor("ban.confirm", event.getGuild())).queue();

            MODOLOGGER.info(event.getAuthor().getName() + " banned " + message.getMentionedMembers().get(0).getEffectiveName() + " for: " + reason);
        }
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Ban a member", "Usage: `" + Constants.PREFIX + getInvoke() + " <@user> [reason]`", false);
    }

    @Override
    public boolean haveEvent() {
        return false;
    }

    @Override
    public void onEvent(GenericEvent event) {}

    @Override
    public String getInvoke() {
        return "ban";
    }

    @Override
    public Type getType() {
        return super.getType();
    }

    @Override
    public Collection<net.dv8tion.jda.api.Permission> requiredDiscordPermission() {
        return Arrays.asList(net.dv8tion.jda.api.Permission.BAN_MEMBERS, net.dv8tion.jda.api.Permission.MESSAGE_EMBED_LINKS);
    }
}
