package fr.aven.bot.commands.modo;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.entity.Warn;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.dv8tion.jda.api.requests.ErrorResponse;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class WarnCommand implements ICommand
{
    @Override
    public void handle(List<String> args, CommandEvent event) {
        if (args.isEmpty())
        {
            event.getChannel().sendMessageEmbeds(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        if (event.message().getMentionedUsers().isEmpty())
        {
            event.getChannel().sendMessageEmbeds(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        User user = event.message().getMentionedUsers().get(0);

        if (user.getId().equalsIgnoreCase(event.message().getAuthor().getId()))
        {
            event.getChannel().sendMessageEmbeds(new EmbedBuilder().setDescription("You can't warn yourself!").setColor(Color.RED).build()).queue();
            return;
        }

        String reason = StringUtils.join(args, " ").replaceFirst(user.getAsTag(), "");
        Warn warn = new Warn(user.getId(), event.getGuild().getId(), event.getAuthor().getId(), reason, event.message().getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME));
        Main.getDatabase().addWarn(warn);

        EmbedBuilder builder = new EmbedBuilder()
                .setAuthor(Main.getLanguage().getTextFor("warn.title",event.getGuild()), "https://justaven.xyz")
                .addField(Main.getLanguage().getTextFor("warn.user", event.getGuild()), user.getAsTag(), true)
                .addField(Main.getLanguage().getTextFor("warn.moderator", event.getGuild()), event.getAuthor().getAsTag(), true)
                .addField(Main.getLanguage().getTextFor("warn.reason", event.getGuild()), reason, false)
                .setFooter("Date: "+ event.message().getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME))
                .setThumbnail(user.getAvatarUrl());

        event.getChannel().sendMessageEmbeds(builder.build()).queue();
        user.openPrivateChannel().queue(pc -> pc.sendMessage(new MessageBuilder()
                .appendFormat(Main.getLanguage().getTextFor("warn.mp", event.getGuild()), event.getGuild().getName())
                .setEmbeds(builder.build()).build()).queue(success -> {}, error -> {
                    var errorException = (ErrorResponseException) error;

                    if (errorException.getErrorResponse().equals(ErrorResponse.CANNOT_SEND_TO_USER))
                        event.getChannel().sendMessage(Main.getLanguage().getTextFor("warn.mpDisabled", event.getGuild())).queue();
        }));


        List<Warn> warns = Main.getDatabase().listWarns(user.getId(), event.getGuild().getId());
        Integer warnsLimit = Main.getDatabase().getWarnLimit(event.getGuild());

        if (warnsLimit != 0)
            if (warns.size() == warnsLimit)
                switch (Main.getDatabase().getWarnLimitType(event.getGuild()))
                {
                    case "ban":
                        event.getGuild().ban(user, 7, "Automod (Warn)").queue();
                        break;
                    case "kick":
                        event.getGuild().retrieveMember(user).queue(member -> event.getGuild().kick(member, "Automod (warn)").queue());
                        break;
                }
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
        return new MessageEmbed.Field("Warn a member", "Usage: `" + Constants.PREFIX + getInvoke() + " <@user> [reason]`", false);
    }

    @Override
    public String getInvoke() {
        return "warn";
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
        return Arrays.asList(net.dv8tion.jda.api.Permission.MANAGE_ROLES, net.dv8tion.jda.api.Permission.MESSAGE_EMBED_LINKS);
    }
}
