package fr.aven.bot.commands.admin.announce;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class AnnounceCommand implements ICommand
{
    private List<String> possibility = Arrays.asList("ban", "join", "leave");

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if (event.getMessage().getMentionedChannels().size() == 0)
        {
            event.getChannel().sendMessage(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        if (!possibility.contains(args.get(0)))
        {
            event.getChannel().sendMessage(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        if (args.get(1).equalsIgnoreCase("remove"))
        {
            try {
                Main.getDatabase().setTextJLB(event.getGuild().getId(), "", args.get(0));
                event.getMessage().addReaction("\uD83D\uDC4C").queue();
            } catch (SQLException sqlException) {
                event.getChannel().sendMessage("An error has occured, please retry.").queue();
            }

        }

        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.size(); i++)
        {
            if (!builder.toString().equalsIgnoreCase("")) builder.append(" ");
            builder.append(args.get(i));
        }

        try {
            Main.getDatabase().setTextJLB(event.getGuild().getId(), builder.toString(), args.get(0));

            String text = builder.toString().replaceAll("<guild>", event.getGuild().getName()).replaceAll("<member>", event.getAuthor().getAsMention());

            event.getChannel().sendMessage("Example : \n"+ text).queue();
        } catch (SQLException sqlException) {
            event.getChannel().sendMessage("An error has occured, please retry.").queue();
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
                "\nWarning:" +
                "\n❱ To tag the member: use <member>"+
                "\n❱ To add the server name: use <guild>"+
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
}
