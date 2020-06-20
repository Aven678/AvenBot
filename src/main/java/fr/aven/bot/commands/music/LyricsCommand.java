package fr.aven.bot.commands.music;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.music.PlayerManager;
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.explodingbush.ksoftapi.entities.Lyric;

import java.util.List;

public class LyricsCommand implements ICommand
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        Lyric lyrics;

        if (args.size() == 0)
        {
            if (!PlayerManager.getInstance().checkNullForEvent(event.getGuild())) {
                lyrics = Main.getkSoft().getLyrics(PlayerManager.getInstance().getGuildMusicManager(event.getGuild(), event.getChannel()).player.getPlayingTrack().getInfo().title);
            } else{
                event.getChannel().sendMessage(Main.getDatabase().getTextFor("argsNotFound", event.getGuild())).queue();
                return;
            }
        }
        else
        {
            String input = String.join(" ", args);
            lyrics = Main.getkSoft().getLyrics(input);
        }

        if (lyrics == null)
        {
            EmbedBuilder builder = new EmbedBuilder();

            builder.setTitle(Main.getDatabase().getTextFor("error", event.getGuild()),"https://justaven.xyz");
            builder.setDescription(Main.getDatabase().getTextFor("notFound", event.getGuild()));

            event.getChannel().sendMessage(builder.build()).queue();
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(Main.getDatabase().getTextFor("lyrics.title", event.getGuild()) + lyrics.getTitle(), "https://justaven.xyz", lyrics.getAlbumArtUrl());

        if (lyrics.getLyrics().length() > MessageEmbed.TEXT_MAX_LENGTH)
        {
            String[] lyricsLine = lyrics.getLyrics().split("\n");
            int totalLine = lyricsLine.length;
            int middle = totalLine / 2;
            StringBuilder firstLyrics = new StringBuilder();
            StringBuilder secondPart = new StringBuilder();

            for (int i = 0; i < middle; i++) {
                if (!firstLyrics.toString().equalsIgnoreCase(""))
                    firstLyrics.append("\n");
                firstLyrics.append(lyricsLine[i]);
            }

            for (int i = middle+1; i < lyricsLine.length; i++) {
                if (!secondPart.toString().equalsIgnoreCase(""))
                    secondPart.append("\n");
                secondPart.append(lyricsLine[i]);
            }

            EmbedBuilder secondPartBuilder = new EmbedBuilder();
            builder.setDescription(firstLyrics.toString());
            secondPartBuilder.setDescription(secondPart.toString());
            secondPartBuilder.setFooter("Lyrics by KSoft.Si");

            sendLyrics(event.getChannel(), builder, secondPartBuilder);


        } else {
            builder.setDescription(lyrics.getLyrics());
            builder.setFooter("Lyrics by KSoft.Si");
            sendLyrics(event.getChannel() ,builder);
        }

    }

    public void sendLyrics(TextChannel channel, EmbedBuilder... builders)
    {
        for (EmbedBuilder builder :  builders)
        {
            if (builder.isEmpty()) continue;
            channel.sendMessage(builder.build()).queue();
        }
    }

    @Override
    public Type getType() {
        return Type.MUSIC;
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Get lyrics of a song.", "Usage: `" + Constants.PREFIX + getInvoke() + " <query>`", false);
    }

    @Override
    public String getInvoke() {
        return null;
    }

    @Override
    public boolean haveEvent() {
        return false;
    }

    @Override
    public void onEvent(GenericEvent event) {

    }
}
