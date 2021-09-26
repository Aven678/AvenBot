package fr.aven.bot.commands.util;

import fr.aven.bot.Constants;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import fr.aven.bot.util.MessageUtil;
import fr.aven.bot.util.OMDB;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class OMDBCommand implements ICommand
{
    private OMDB omdb = new OMDB();

    @Override
    public void handle(List<String> args, CommandEvent event) {
        if (args.size() == 0)
        {
            event.getChannel().sendMessage(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        try
        {
            var movie = omdb.findMovie(StringUtils.join(args, " "));

            if (!movie.success())
            {
                event.getChannel().sendMessage("❌ An error has occured: "+movie.error()).queue();
                return;

            }

            sendMessage(movie, event);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(OMDB.OMDBMovie movie, CommandEvent event)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(upperCaseFirst(movie.Type)+": "+movie.Title, "https://www.justaven.xyz", event.getAuthor().getAvatarUrl());


        builder.setImage(movie.Poster.equals("N/A") ? null : movie.Poster);
        builder.addField("Released", movie.Released, true);
        builder.addField("Director", movie.Director, true);
        builder.addField("Genre", movie.Genre, false);
        builder.addField("Runtime", movie.Runtime, true);
        builder.addField("Country", movie.Country, true);
        builder.addField("Actors", movie.Actors, false);
        builder.addField("Awards", movie.Awards, false);
        builder.addField("Rating", movie.imdbRating+"/10", true);
        builder.addField("Box-Office", movie.BoxOffice, true);
        builder.addField("Plot", movie.Plot, false);

        builder.setFooter("omdbapi.com | AvenBot by Aven#1000");

        builder.setColor(MessageUtil.getRandomColor());

        event.getChannel().sendMessage(builder.build()).queue();
    }

    public static String upperCaseFirst(String val) {
        char[] arr = val.toCharArray();
        arr[0] = Character.toUpperCase(arr[0]);
        return new String(arr);
    }

    @Override
    public Type getType() {
        return Type.UTIL;
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Shows informations of a movie/serie.", "Usage: `"+ Constants.PREFIX + getInvoke() +"` <title>", false);
    }

    @Override
    public String getInvoke() {
        return "omdb";
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
