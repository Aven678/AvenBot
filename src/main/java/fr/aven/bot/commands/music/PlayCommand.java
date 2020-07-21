package fr.aven.bot.commands.music;

import com.google.api.services.youtube.model.SearchResult;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.music.GuildMusicManager;
import fr.aven.bot.music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class PlayCommand extends MusicCommands
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event)
    {
        if (!event.getGuild().getAudioManager().isConnected()) {
                JoinCommand.joinChannel(event);
                return;
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

        if (!isUrl(input))
            input = "ytsearch: "+input;

        manager.loadAndPlay(event.getMessage(), input);
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
}