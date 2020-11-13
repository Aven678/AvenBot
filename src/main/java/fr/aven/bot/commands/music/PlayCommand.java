package fr.aven.bot.commands.music;

import com.google.api.services.youtube.model.SearchResult;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.music.GuildMusicManager;
import fr.aven.bot.music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class PlayCommand extends MusicCommands
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event)
    {
        if (!event.getGuild().getAudioManager().isConnected()) {
                JoinCommand.joinChannel(event);
        }

        TextChannel channel = event.getChannel();
        PlayerManager manager = PlayerManager.getInstance();

        if (args.isEmpty())
        {
            if (manager.getGuildMusicManager(event.getGuild(), event.getChannel()).scheduler.getQueue().isEmpty())
                channel.sendMessage(Main.getDatabase().getTextFor("argsNotFound", event.getGuild())).queue();

            return;
        }

        if (!manager.getGuildMusicManager(event.getGuild(), event.getChannel()).scheduler.search.isEmpty())
        {
            channel.sendMessage(Main.getDatabase().getTextFor("play.confirmChoice", event.getGuild())).queue();
            return;
        }

        /*if (manager.getGuildMusicManager(event.getGuild(), event.getChannel()).player.isPaused())
        {
            manager.getGuildMusicManager(event.getGuild(), event.getChannel()).player.setPaused(false);
            event.getChannel().sendMessage(new EmbedBuilder()
                    .setTitle(Main.getDatabase().getTextFor("success", event.getGuild()))
                    .setDescription(Main.getDatabase().getTextFor("pause.playerResume", event.getGuild()))
                    .build()).queue();

            return;
        }*/

        String input = String.join(" ", args);

        if (isSpotifyTrackURL(input))
        {
            manager.loadAndPlaySpotifyTrack(event.getMessage(), Main.getSpotifyAPI().getTrack(input));
        }

        else if (isSpotifyPlaylistURL(input))
        {
            manager.loadAndPlaySpotifyPlaylist(event.getMessage(), Main.getSpotifyAPI().getPlaylistTracks(input));
        }
        else
        {
            if (!isUrl(input))
                input = "ytsearch: "+input;

            manager.loadAndPlay(event.getMessage(), input, false);
        }

    }

    private boolean isUrl(String input)
    {
        try
        {
            new URL(input);

            return true;
        } catch (MalformedURLException ignored)
        {
            return false;
        }
    }

    private boolean isSpotifyTrackURL(String input)
    {
        if (input.startsWith("https://open.spotify.com/track/")) return true;
        return false;
    }

    private boolean isSpotifyPlaylistURL(String input)
    {
        if (input.startsWith("https://open.spotify.com/playlist/")) return true;
        return false;
    }

    @Override
    public boolean haveEvent() {
        return false;
    }

    @Override
    public void onEvent(GenericEvent event) {}

    @Override
    public MessageEmbed.Field getHelp()
    {
        return new MessageEmbed.Field("Plays a song", "Usage: `" + Constants.PREFIX + getInvoke() + " <song url>`", false);
    }

    @Override
    public String getInvoke()
    {
        return "play";
    }

    @Override
    public Type getType() {
        return super.getType();
    }

    @Override
    public Permission getPermission()
    {
        return Permission.USER;
    }

    @Override
    public Collection<net.dv8tion.jda.api.Permission> requiredDiscordPermission() {
        return Arrays.asList(net.dv8tion.jda.api.Permission.MESSAGE_WRITE, net.dv8tion.jda.api.Permission.MESSAGE_EMBED_LINKS);
    }
}