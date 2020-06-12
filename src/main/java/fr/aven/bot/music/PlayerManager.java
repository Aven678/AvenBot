package fr.aven.bot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager
{
    private static PlayerManager INSTANCE;
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    private PlayerManager()
    {
        this.musicManagers = new HashMap<>();

        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerLocalSource(playerManager);
        AudioSourceManagers.registerRemoteSources(playerManager);
    }

    public boolean checkNullForEvent(Guild guild)
    {
        return musicManagers.get(guild.getIdLong()) == null ? true : false;
    }

    public void destroyGuildMusicManager(Guild guild)
    {
        if (musicManagers.containsKey(guild.getIdLong()))
            musicManagers.remove(guild.getIdLong());
    }

    public synchronized GuildMusicManager getGuildMusicManager(Guild guild, TextChannel channel)
    {
        long guildId = guild.getIdLong();
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null)
        {
            musicManager = new GuildMusicManager(playerManager, guild, channel);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public void loadAndPlay(Message message, String trackUrl, @Nullable AudioTrack possibleTrack)
    {
        GuildMusicManager musicManager = getGuildMusicManager(message.getGuild(), message.getTextChannel());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler()
        {
            @Override
            public void trackLoaded(AudioTrack track)
            {
                String title;
                String author;
                EmbedBuilder builder = new EmbedBuilder();
                builder.setThumbnail("https://i.ytimg.com/vi/" + track.getInfo().identifier + "/hqdefault.jpg");
                title = track.getInfo().title;
                author = track.getInfo().author;

                message.getChannel().sendMessage(builder.setAuthor("Music added", track.getInfo().uri, message.getJDA().getSelfUser().getAvatarUrl())
                        .addField("❱ Author : "+author, "❱ "+title, false)
                        .setColor(new Color(0, 255, 151))
                        .setFooter("AvenBot by Aven#1000").build()).queue();

                musicManager.scheduler.usersRequest.put(track, message.getAuthor().getIdLong());
                play(musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist)
            {
                if (playlist.isSearchResult())
                {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setAuthor("Music search", "https://justaven.com", message.getAuthor().getAvatarUrl());
                    builder.setColor(message.getMember().getColor());
                    builder.setFooter("Enter the number of your choice.", message.getJDA().getSelfUser().getAvatarUrl());

                    for (int i = 0; i < playlist.getTracks().size() && i < 5; i++)
                    {
                        AudioTrack track = playlist.getTracks().get(i);
                        AudioTrackInfo info = track.getInfo();
                        int nbTrack = i;
                        nbTrack++;
                        builder.appendDescription("\n`" + nbTrack + "`: **" + info.title + "** | Author : " + info.author);

                        musicManager.scheduler.search.put(nbTrack, track);
                    }

                    message.getTextChannel().sendMessage(builder.build()).queue(msg -> msg.addReaction("❌").queue());
                } else
                {
                    AudioTrack firstTrack = playlist.getSelectedTrack();

                    if (firstTrack == null)
                    {
                        firstTrack = playlist.getTracks().remove(0);
                    }

                    message.getChannel().sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

                    play(musicManager, firstTrack);

                    playlist.getTracks().forEach(musicManager.scheduler::queue);
                    for (AudioTrack track : playlist.getTracks())
                        musicManager.scheduler.usersRequest.put(track, message.getAuthor().getIdLong());
                }
            }

            @Override
            public void noMatches()
            {
                message.getChannel().sendMessage("Nothing found, please retry.").queue();
            }

            @Override
            public void loadFailed(FriendlyException exception)
            {
                message.getChannel().sendMessage("Could not play: " + exception.getMessage()).queue();
            }
        });

    }

    private void play(GuildMusicManager musicManager, AudioTrack track)
    {
        musicManager.scheduler.queue(track);
    }

    public static synchronized PlayerManager getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new PlayerManager();
        }

        return INSTANCE;
    }
}
