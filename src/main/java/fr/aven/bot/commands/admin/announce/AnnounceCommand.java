package fr.aven.bot.commands.admin.announce;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AnnounceCommand implements ICommand
{
    private List<String> possibility = Arrays.asList("ban", "join", "leave");

    @Override
    public void handle(List<String> args, CommandEvent event) {
        if (args.isEmpty())
        {
            event.getChannel().sendMessageEmbeds(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        if (args.get(0).equalsIgnoreCase("channel"))
        {
            if (event.message().getMentionedChannels().size() == 0)
            {
                event.getChannel().sendMessageEmbeds(new EmbedBuilder().addField(getHelp()).build()).queue();
                return;
            }

            TextChannel channel = event.message().getMentionedChannels().get(0);
            Main.getDatabase().setAnnounceChannel(channel, event.getGuild());

            event.message().addReaction("\uD83D\uDC4C").queue();

            return;
        }

        if (!possibility.contains(args.get(0)))
        {
            event.getChannel().sendMessageEmbeds(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        if (args.get(1).equalsIgnoreCase("remove"))
        {
            try {
                Main.getDatabase().setTextJLB(event.getGuild().getId(), "", args.get(0));
                event.message().addReaction("\uD83D\uDC4C").queue();
            } catch (SQLException sqlException) {
                event.getChannel().sendMessage("An error has occured, please retry.").queue();
                event.getJDA().getTextChannelById(871509862109020191L).sendMessage("Nouvelle erreur : " +
                        "\n```java \n"+sqlException.getMessage()+"\n```").queue();
            }

        }

        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.size(); i++)
        {
            if (!builder.toString().equalsIgnoreCase("")) builder.append(" ");
            builder.append(args.get(i));
        }

        String type = "";

        switch (args.get(0))
        {
            case "join":
                type = "textJoin";
                break;
            case "leave":
                type = "textLeave";
                break;
            case "ban":
                type = "textBan";
                break;
        }

        try {
            Main.getDatabase().setTextJLB(event.getGuild().getId(), builder.toString(), type);

            String text = builder.toString().replaceAll("<guild>", event.getGuild().getName()).replaceAll("<member>", event.getAuthor().getAsMention())
                    .replaceAll("<number>", String.valueOf(event.getGuild().getMembers().size()));

            event.getChannel().sendMessage("Example : \n"+ text).queue();
        } catch (SQLException sqlException) {
            event.getChannel().sendMessage("An error has occured, please retry.").queue();
            event.getJDA().getTextChannelById(463270369550139422L).sendMessage("Nouvelle erreur : " +
                    "\n```java \n"+sqlException.getMessage()+"\n```").queue();
        }

    }

    @Override
    public Type getType() {
        return Type.ADMIN;
    }

    @Override
    public Permission getPermission() {
        return Permission.ADMIN;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Set the announce message for member activity (join, leave, ban).", "Usage: `" + Constants.PREFIX + getInvoke() + "` <ban/join/leave> <remove/your message> " +
                "\nFor set the channel: use "+ Constants.PREFIX + getInvoke() + " channel #channel" +
                "\nWarning:" +
                "\n❱ To tag the member: use <member>"+
                "\n❱ To add the server name: use <guild>"+
                "\n❱ To add the total number of members: use <number>"+
                "\nExample: "+Constants.PREFIX + getInvoke()+ " ban Oops, <member> has been banned in <guild> server !", false);
    }

    @Override
    public String getInvoke() {
        return "announce";
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
