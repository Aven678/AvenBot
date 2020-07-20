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

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class KickCommand extends ModoCommands {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event)
    {
        TextChannel channel = event.getChannel();
        Message message = event.getMessage();

        if (!message.getGuild().getSelfMember().hasPermission(net.dv8tion.jda.api.Permission.KICK_MEMBERS))
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
        }
        else if (message.getMentionedUsers().isEmpty())
        {
            channel.sendMessage(Main.getDatabase().getTextFor("kick.notMentionned", event.getGuild())).queue();

            return;
        }

        if (message.toString().length() > 2)
        {

            String reason;

            StringBuilder reasonBuilder = new StringBuilder();
            for (int i = 1; i < args.size(); i++)
            {
                reasonBuilder.append(args.get(i));
            }

            reason = reasonBuilder.toString();
            Main.getDatabase().addKick(message.getMentionedUsers().get(0).getId(), event.getGuild().getId(), message.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), reason);
            event.getGuild().kick(message.getMentionedMembers().get(0), reason).queue();

            channel.sendMessage(Main.getDatabase().getTextFor("kick.confirm", event.getGuild())).queue();

            MODOLOGGER.info(event.getAuthor().getName() + " kicked " + message.getMentionedMembers().get(0).getEffectiveName() + " for: " + reason);

        }
    }

    @Override
    public MessageEmbed.Field getHelp()
    {
        return new MessageEmbed.Field("Kick a member", "Usage: `" + Constants.PREFIX + getInvoke() + " <@user> [reason]`", false);
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
}
