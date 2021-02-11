package fr.aven.bot.modules.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.wrapper.spotify.model_objects.specification.Track;
import fr.aven.bot.Main;
import fr.aven.bot.util.MessageTask;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.*;

public class PlayerManager
{
    private static PlayerManager INSTANCE;
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    private PlayerManager()
    {
        this.musicManagers = new HashMap<>();

        this.playerManager = new DefaultAudioPlayerManager();
        /*YoutubeAudioSourceManager youtubemanger = new YoutubeAudioSourceManager();

        playerManager.registerSourceManager(youtubemanger);
        playerManager.registerSourceManager(new SpotifyAudioSourceManager(Main.getConfiguration().getString("spotify.clientID", ""),
                Main.getConfiguration().getString("spotify.clientSecret", ""),
                Main.getConfiguration().getString("youtubeAPI", ""),
                youtubemanger));*/

        AudioSourceManagers.registerLocalSource(playerManager);
        AudioSourceManagers.registerRemoteSources(playerManager);
    }

    public boolean checkNullForEvent(Guild guild)
    {
        return musicManagers.get(guild.getIdLong()) == null;
    }

    /*public void destroyGuildMusicManager(Guild guild)
    {
        musicManagers.remove(guild.getIdLong());
    }*/

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

    /*public void loadAndPlaySpotifyPlaylist(Message message, Paging<PlaylistTrack> playlistTracks) {
        final Long[] messageID = {0L};

        message.getChannel().sendMessage("Playlist added ! Please wait...").queue(msg -> messageID[0] = msg.getIdLong());

        List<AudioTrack> audioTracks = new ArrayList<>();
        GuildMusicManager musicManager = getGuildMusicManager(message.getGuild(), message.getTextChannel());

        for (int i = 0; i < playlistTracks.getTotal(); i++) {
            PlaylistTrack playlistTrack = playlistTracks.getItems()[i];
            if (playlistTrack.getIsLocal()) continue;
            Track track = (Track) playlistTrack.getTrack();

            String search = "ytsearch:" + track.getName() + " " + track.getArtists()[0].getName();

            playerManager.setFrameBufferDuration(5000);
            int finalI = i;
            playerManager.loadItemOrdered(musicManager, search, new AudioLoadResultHandler() {

                @Override
                public void trackLoaded(AudioTrack track) {
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    if (playlist.isSearchResult())
                        if (playlist.getSelectedTrack() == null)
                            audioTracks.add(playlist.getTracks().get(1));
                        else
                            audioTracks.add(playlist.getSelectedTrack());


                    int j = finalI;
                    j++;
                    message.getChannel().editMessageById(messageID[0], "Playlist added ! Please wait... ("+j+"/"+playlistTracks.getTotal()+")").queue();

                    if (j == playlistTracks.getTotal())
                    {
                        playSpotify();
                        message.getChannel().editMessageById(messageID[0], "✅ Playlist added ! Please wait... ("+j+"/"+playlistTracks.getTotal()+")").queue();
                    }


                }

                @Override
                public void noMatches() {
                }

                @Override
                public void loadFailed(FriendlyException exception) {
                    exception.printStackTrace();
                }

                public void playSpotify()
                {
                    AudioTrack firstTrack = audioTracks.get(0);
                    musicManager.scheduler.usersRequest.put(firstTrack, message.getAuthor().getIdLong());

                    for (int j = 1; j < audioTracks.size(); j++)
                        musicManager.scheduler.usersRequest.put(audioTracks.get(j), message.getAuthor().getIdLong());

                    play(musicManager, firstTrack, message.getTextChannel());
                    audioTracks.remove(0);
                    audioTracks.forEach(musicManager.scheduler::queue);

                };
        });
    }

    }*/

    public void loadAndPlayDeezerTrack(Message message, deezer.model.Track track)
    {
        String search = "ytsearch:" + track.getTitle()+" "+track.getArtist().getName();

        loadAndPlay(message, search, false, true, false);
    }

    public void loadAndPlaySpotifyTrack(Message message, Track track)
    {
        String search = "ytsearch:"+ track.getName()+" "+ track.getArtists()[0].getName();

        loadAndPlay(message,search, true, false, false);
    }

    public void loadAndPlay(Message message, String trackUrl, boolean spotify, boolean deezer, boolean file)
    {
        GuildMusicManager musicManager = getGuildMusicManager(message.getGuild(), message.getTextChannel());

        playerManager.setFrameBufferDuration(5000);
        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler()
        {
            @Override
            public void trackLoaded(AudioTrack track)
            {
                String title;
                String author;
                EmbedBuilder builder = new EmbedBuilder();
                if (!file) builder.setThumbnail("https://i.ytimg.com/vi/" + track.getInfo().identifier + "/maxresdefault.jpg");
                title = track.getInfo().title;
                author = track.getInfo().author;

                message.getChannel().sendMessage(builder.setAuthor(Main.getDatabase().getTextFor("music.add", message.getGuild()), track.getInfo().uri, message.getJDA().getSelfUser().getAvatarUrl())
                        .addField("❱ "+Main.getDatabase().getTextFor("music.author", message.getGuild())+" : "+author, "❱ "+title, false)
                        .setColor(new Color(0, 255, 151))
                        .setFooter("AvenBot by Aven#1000").build()).queue(msg -> {
                            new Timer().schedule(new MessageTask(msg), 10000);
                });

                musicManager.scheduler.usersRequest.put(track, message.getAuthor().getIdLong());
                play(musicManager, track, message.getTextChannel());
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                if (playlist.isSearchResult()) {
                    if (spotify) {
                        loadAndPlayWithPlatforms(playlist, message, musicManager, "Spotify");
                    } else if (deezer) {
                        loadAndPlayWithPlatforms(playlist, message, musicManager, "Deezer");
                    } else {

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setAuthor(Main.getDatabase().getTextFor("music.searchTitle", message.getGuild()), "https://justaven.xyz", message.getAuthor().getAvatarUrl());
                        builder.setColor(message.getMember().getColor());
                        builder.setFooter(Main.getDatabase().getTextFor("music.searchFooter", message.getGuild()), message.getJDA().getSelfUser().getAvatarUrl());

                        for (int i = 0; i < playlist.getTracks().size() && i < 5; i++) {
                            AudioTrack track = playlist.getTracks().get(i);
                            AudioTrackInfo info = track.getInfo();
                            int nbTrack = i;
                            nbTrack++;
                            builder.appendDescription("\n`" + nbTrack + "`: **" + info.title + "** | " + Main.getDatabase().getTextFor("music.author", message.getGuild()) + " : " + info.author);

                            musicManager.scheduler.search.put(nbTrack, track);
                        }

                        message.getTextChannel().sendMessage(builder.build()).queue(msg -> {
                            msg.addReaction("❌").queue();
                            musicManager.scheduler.lastMessageSearch = msg;
                        });
                    }
                } else {
                    AudioTrack firstTrack = playlist.getSelectedTrack();

                    if (firstTrack == null) {
                        firstTrack = playlist.getTracks().remove(0);
                    }

                    //message.getChannel().sendMessageFormat(Main.getDatabase().getTextFor("music.playlistAdd", message.getGuild()), firstTrack.getInfo().title, playlist.getName()).queue();

                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setAuthor(Main.getDatabase().getTextFor("playlist.title", message.getGuild()));
                    builder.addField("❱ "+playlist.getName() + " (" + playlist.getTracks().size() + " tracks)",
                            "❱ "+ Main.getDatabase().getTextFor("playlist.firstTrack", message.getGuild()) + " : " + playlist.getTracks().get(0).getInfo().title,
                            false).setFooter("AvenBot by Aven#1000").setColor(new Color(255, 127, 0));

                    message.getChannel().sendMessage(builder.build()).queue(msg -> {
                        new Timer().schedule(new MessageTask(msg), 10000);
                    });

                    musicManager.scheduler.usersRequest.put(firstTrack, message.getAuthor().getIdLong());
                    for (AudioTrack track : playlist.getTracks())
                        musicManager.scheduler.usersRequest.put(track, message.getAuthor().getIdLong());

                    play(musicManager, firstTrack, message.getTextChannel());
                    playlist.getTracks().forEach(musicManager.scheduler::queue);

                }

            }
            @Override
            public void noMatches()
            {
                message.getChannel().sendMessage(Main.getDatabase().getTextFor("music.notFound", message.getGuild())).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception)
            {
                exception.printStackTrace();
                message.getChannel().sendMessage(Main.getDatabase().getTextFor("music.couldntPlay", message.getGuild()) + exception.getMessage()).queue();
            }
        });

    }

    private void loadAndPlayWithPlatforms(AudioPlaylist playlist, Message message, GuildMusicManager musicManager, String platform)
    {
        AudioTrack audioTrack = playlist.getTracks().get(0);


        String title;
        String author;
        EmbedBuilder builder = new EmbedBuilder();
        builder.setThumbnail("https://i.ytimg.com/vi/" + audioTrack.getInfo().identifier + "/maxresdefault.jpg");
        title = audioTrack.getInfo().title;
        author = audioTrack.getInfo().author;

        message.getChannel().sendMessage(builder.setAuthor(Main.getDatabase().getTextFor("music.add", message.getGuild()) + (" ("+platform+")"), audioTrack.getInfo().uri, message.getJDA().getSelfUser().getAvatarUrl())
                .addField("❱ " + Main.getDatabase().getTextFor("music.author", message.getGuild()) + " : " + author, "❱ " + title, false)
                .setColor(new Color(0, 255, 151))
                .setFooter("AvenBot by Aven#1000").build()).queue(msg -> {
            new Timer().schedule(new MessageTask(msg), 10000);
        });

        musicManager.scheduler.usersRequest.put(audioTrack, message.getAuthor().getIdLong());
        play(musicManager, audioTrack, message.getTextChannel());
    }

    private void play(GuildMusicManager musicManager, AudioTrack track, TextChannel channel)
    {
        if (musicManager.scheduler.getQueue().isEmpty()) musicManager.scheduler.channel = channel;
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
