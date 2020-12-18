package fr.aven.bot.modules.jda.events;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import fr.aven.bot.Main;
import fr.aven.bot.commands.music.LyricsCommand;
import fr.aven.bot.modules.music.GuildMusicManager;
import fr.aven.bot.modules.music.PlayerManager;
import fr.aven.bot.util.ICommand;
import fr.aven.bot.util.MessageTask;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

public class MusicReactionListener extends ListenerAdapter
{
    private List<String> emotes = Arrays.asList("⏮️","⏯️", "⏭️", "\uD83D\uDD01", "\uD83D\uDCDC", "❌");

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        if (event.getUser().isBot()) return;
        if (event.getGuild() == null) return;
        if (!event.getGuild().getAudioManager().isConnected()) return;
        GuildMusicManager manager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild(), event.getChannel());

        if (event.getMessageIdLong() != manager.scheduler.lastMessageStatus.getIdLong())
            return;

        if (!emotes.contains(event.getReactionEmote().getName())) {
            deleteReaction(event);
            return;
        }

        deleteReaction(event);

        AudioTrack track = manager.player.getPlayingTrack();
        switch (event.getReactionEmote().getName())
        {
            case "⏮️": //old
                if (Main.getDatabase().checkPermission(event.getGuild(), event.getUser(), ICommand.Permission.DJ, event.getChannel())) {
                    manager.player.stopTrack();
                    manager.scheduler.nextTrack(manager.scheduler.oldTrack, true);
                } else
                    missingPermission(event);
                break;
            case "⏯️": //playpause
                if (manager.player.isPaused()) {
                    manager.player.setPaused(false);
                } else {
                    manager.player.setPaused(true);
                }

                manager.scheduler.editMessage();
                break;
            case "⏭️": //skip
                if (Main.getDatabase().checkPermission(event.getGuild(), event.getUser(), ICommand.Permission.DJ, event.getChannel())) {
                    manager.player.stopTrack();
                    manager.scheduler.nextTrack(track, false);
                } else
                    missingPermission(event);
                break;
            case "\uD83D\uDD01": //repeat
                manager.scheduler.repeat = true;
                manager.scheduler.editMessage();
                break;
            case "\uD83D\uDCDC": //lyrics
                if (!manager.scheduler.lyricsAlwaysRequested){
                    LyricsCommand.sendLyrics(event, Main.getkSoft().getLyrics(track.getInfo().title));
                    manager.scheduler.lyricsAlwaysRequested = true;
                }
                break;
            case "❌": //stop
                if (Main.getDatabase().checkPermission(event.getGuild(), event.getUser(), ICommand.Permission.DJ, event.getChannel())) {
                    manager.player.stopTrack();
                    manager.scheduler.purgeQueue();
                    manager.scheduler.nextTrack(track, false);
                    manager.scheduler.alwaysStopped = true;
                } else
                    missingPermission(event);
                break;
        }

    }

    public void deleteReaction(GuildMessageReactionAddEvent event)
    {
        try {
            event.getReaction().removeReaction(event.getUser()).queue();
        } catch (Exception ignored) {}
    }

    public void missingPermission(GuildMessageReactionAddEvent event)
    {
        event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Error")
                .setDescription("You don't have the permission to execute this command.")
                .setFooter("Command executed by " + event.getUser().getAsTag()).build())
                .queue(msg -> new Timer().schedule(new MessageTask(msg), 5000));
        return;
    }
}
