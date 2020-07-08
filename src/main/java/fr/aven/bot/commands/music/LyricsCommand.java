package fr.aven.bot.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.music.GuildMusicManager;
import fr.aven.bot.music.PlayerManager;
import fr.aven.bot.util.ICommand;
import fr.aven.bot.util.KSoft;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.GenericMessageEvent;
import net.dv8tion.jda.api.events.message.guild.GenericGuildMessageEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.explodingbush.ksoftapi.entities.Lyric;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class LyricsCommand implements ICommand
{
    public static void sendLyrics(GenericGuildMessageEvent event, Lyric lyrics)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(Main.getDatabase().getTextFor("lyrics.title", event.getGuild()) + lyrics.getTitle(), "https://justaven.xyz", lyrics.getAlbumArtUrl());

        if (lyrics.getLyrics().length() > MessageEmbed.TEXT_MAX_LENGTH)
        {
            String[] lyricsLine = lyrics.getLyrics().split("\n");
            int totalLine = lyricsLine.length;
            //int middle = totalLine / 2;
            StringBuilder firstLyrics = new StringBuilder();
            StringBuilder secondPart = new StringBuilder();

            int i1 = 0;
            while (firstLyrics.length() != 2048)
            {
                if (!firstLyrics.toString().equalsIgnoreCase(""))
                    firstLyrics.append("\n");
                firstLyrics.append(lyricsLine[i1]);
                i1++;
            }

            if (firstLyrics.length() > 2048){
                i1 = i1 - 1;
                firstLyrics.setLength(firstLyrics.length() - lyricsLine[i1].length());
            }

            while (secondPart.length() != 2048 || i1 != lyricsLine.length)
            {
                if (!firstLyrics.toString().equalsIgnoreCase(""))
                    firstLyrics.append("\n");
                firstLyrics.append(lyricsLine[i1]);
                i1++;
            }

            /*for (int i = 0; i < middle; i++) {
                if (!firstLyrics.toString().equalsIgnoreCase(""))
                    firstLyrics.append("\n");
                firstLyrics.append(lyricsLine[i]);
            }

            for (int i = middle+1; i < lyricsLine.length; i++) {
                if (!secondPart.toString().equalsIgnoreCase(""))
                    secondPart.append("\n");
                secondPart.append(lyricsLine[i]);
            }*/

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

    public static void sendLyrics(TextChannel channel, EmbedBuilder... builders)
    {
        for (EmbedBuilder builder :  builders)
        {
            if (builder.isEmpty()) continue;
            builder.setColor(Color.ORANGE);
            channel.sendMessage(builder.build()).queue();
        }
    }

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        GuildMusicManager manager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild(), event.getChannel());

        if (args.size() == 0)
        {
            if (!manager.scheduler.getQueue().isEmpty()) {
                sendLyrics(event, Main.getkSoft().getLyrics(PlayerManager.getInstance().getGuildMusicManager(event.getGuild(), event.getChannel()).player.getPlayingTrack().getInfo().title));
            } else{
                event.getChannel().sendMessage(Main.getDatabase().getTextFor("argsNotFound", event.getGuild())).queue();
            }

            return;
        }

        String input = String.join(" ", args);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(Main.getDatabase().getTextFor("lyrics.searchTitle", event.getGuild()), "https://justaven.com", event.getAuthor().getAvatarUrl());
        builder.setColor(event.getMember().getColor());
        builder.setFooter(Main.getDatabase().getTextFor("music.searchFooter", event.getGuild()), event.getJDA().getSelfUser().getAvatarUrl());

        List<Lyric> lyricList = Main.getkSoft().getLyricsList(input);
        for (int i = 0; i < lyricList.size() && i < 5; i++)
        {
            Lyric lyric = lyricList.get(i);
            int nbTrack = i;
            nbTrack++;
            builder.appendDescription("\n`" + nbTrack + "`: **"+ lyric.getTitle() + "** | " + Main.getDatabase().getTextFor("music.author", event.getGuild()) + " : " + lyric.getArtistName());
            manager.scheduler.putLyricsMap(nbTrack, lyric);
        }

        event.getChannel().sendMessage(builder.build()).queue(msg -> msg.addReaction("❌").queue());
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
        return "lyrics";
    }

    @Override
    public boolean haveEvent() {
        return false;
    }

    @Override
    public void onEvent(GenericEvent event) {

    }
}
