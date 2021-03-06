package fr.aven.bot.commands.music;

import deezer.client.DeezerClient;
import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.music.PlayerManager;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PlayCommand extends MusicCommands
{
    private DeezerClient deezerClient = new DeezerClient();

    @Override
    public void handle(List<String> args, CommandEvent event)
    {
        if (!event.getGuild().getAudioManager().isConnected()) {
                if (!JoinCommand.joinChannel(event)) return;
        }

        TextChannel channel = event.getChannel();
        PlayerManager manager = PlayerManager.getInstance();

        if (args.isEmpty())
        {
            if (event.message().getAttachments().size() > 0)
            {
                manager.loadAndPlay(event.message(), event.message().getAttachments().get(0).getUrl(), false, false, true);
                return;
            }

            if (manager.getGuildMusicManager(event.getGuild(), event.getChannel()).scheduler.getQueue().isEmpty())
                channel.sendMessage(Main.getLanguage().getTextFor("argsNotFound", event.getGuild())).queue();

            return;
        }

        if (!manager.getGuildMusicManager(event.getGuild(), event.getChannel()).scheduler.search.isEmpty())
        {
            channel.sendMessage(Main.getLanguage().getTextFor("play.confirmChoice", event.getGuild())).queue();
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

        if (isDeezerTrackURL(input))
        {
            manager.loadAndPlayDeezerTrack(event.message(), deezerClient.getTrack(getDeezerTrackId(input)));
        }

        else if (isSpotifyTrackURL(input))
        {
            manager.loadAndPlaySpotifyTrack(event.message(), Main.getSpotifyAPI().getTrack(input));
        }

        else if (isSpotifyPlaylistURL(input))
        {
            manager.loadAndPlaySpotifyPlaylist(event.message(), Main.getSpotifyAPI().getPlaylistTracks(input));
        }
        else
        {
            if (!isUrl(input))
                input = "ytsearch: "+input;

            manager.loadAndPlay(event.message(), input, false, false, false);
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

    private boolean isDeezerTrackURL(String input)
    {
        try {
            URL url = new URL(input);

            if (url.getHost().equalsIgnoreCase("www.deezer.com") || url.getHost().equalsIgnoreCase("deezer.com"))
            {
                if (Paths.get(url.getPath()).getName(1).toString().equalsIgnoreCase("track"))
                    return true;
            }
        } catch (MalformedURLException | NumberFormatException e) {

        }
        return false;
    }

    private long getDeezerTrackId(String input)
    {
        try {
            URL url = new URL(input);

            /*String path = url.getPath().replaceFirst("/", "");
            String pathV1 = path.substring(path.indexOf("/" ));
            String pathV2 = pathV1.substring("/track/".length());

            System.out.println(pathV2);*/
            return Long.parseLong(Paths.get(url.getPath()).getName(2).toString());
        } catch (NumberFormatException | MalformedURLException e) {
            e.printStackTrace();
        }

        return 0;
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
        return new MessageEmbed.Field(getDescription(), "Usage: `" + Constants.PREFIX + getInvoke() + " <song url>`", false);
    }

    @Override
    public String getInvoke()
    {
        return "play";
    }

    @Override
    public String getDescription() {
        return "Plays a song";
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