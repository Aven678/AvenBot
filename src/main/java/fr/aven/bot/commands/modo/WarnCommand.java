package fr.aven.bot.commands.modo;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.entity.Warn;
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class WarnCommand implements ICommand
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if (args.isEmpty())
        {
            event.getChannel().sendMessage(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        if (event.getMessage().getMentionedUsers().isEmpty())
        {
            event.getChannel().sendMessage(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        User user = event.getMessage().getMentionedUsers().get(0);
        StringBuilder reasonBuilder = new StringBuilder();
        for (int i = 1; i < args.size(); i++) {
            if (!reasonBuilder.toString().equalsIgnoreCase("")) reasonBuilder.append("\n");
            reasonBuilder.append(args.get(i));
        }

        String reason = reasonBuilder.toString();
        Warn warn = new Warn(user.getId(), event.getGuild().getId(), event.getAuthor().getId(), reason, event.getMessage().getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME));
        Main.getDatabase().addWarn(warn);

        EmbedBuilder builder = new EmbedBuilder()
                .setAuthor(Main.getDatabase().getTextFor("warn.title",event.getGuild()), "https://justaven.xyz")
                .addField(Main.getDatabase().getTextFor("warn.user", event.getGuild()), user.getAsTag(), true)
                .addField(Main.getDatabase().getTextFor("warn.moderator", event.getGuild()), event.getAuthor().getAsTag(), true)
                .addField(Main.getDatabase().getTextFor("warn.reason", event.getGuild()), reason, false)
                .setFooter("Date: "+ event.getMessage().getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME))
                .setThumbnail(user.getAvatarUrl());

        event.getChannel().sendMessage(builder.build()).queue();
        if (user.hasPrivateChannel()) user.openPrivateChannel().queue(pc -> pc.sendMessage(new MessageBuilder()
                .appendFormat(Main.getDatabase().getTextFor("warn.mp", event.getGuild()), event.getGuild().getName())
                .setEmbed(builder.build()).build()).queue());
        else
            event.getChannel().sendMessage(Main.getDatabase().getTextFor("warn.mpDisabled", event.getGuild())).queue();

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
