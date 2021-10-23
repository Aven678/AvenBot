package fr.aven.bot.modules.jda.events;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import fr.aven.bot.Main;
import fr.aven.bot.commands.music.LyricsCommand;
import fr.aven.bot.modules.music.GuildMusicManager;
import fr.aven.bot.modules.music.PlayerManager;
import fr.aven.bot.modules.core.ICommand;
import fr.aven.bot.modules.music.lyrics.Lyrics;
import fr.aven.bot.modules.music.lyrics.LyricsAPI;
import fr.aven.bot.util.MessageTask;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

public class MusicReactionListener extends ListenerAdapter
{
    private List<String> emotes = Arrays.asList("⏮️","⏯️", "⏭️", "\uD83D\uDD01", "\uD83D\uDD02", "\uD83D\uDCDC", "❌");
    private List<String> componentIds = Arrays.asList("old", "pause", "skip", "repeat", "repeatOne", "lyrics", "stop");

    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {
        if (event.getUser().isBot()) return;
        if (event.getGuild() == null) return;
        if (!event.getGuild().getAudioManager().isConnected()) return;
        GuildMusicManager manager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild(), event.getTextChannel());

        if (event.getMessageIdLong() != manager.scheduler.lastMessageStatus.getIdLong())
            return;

        if (!componentIds.contains(event.getComponentId())) return;
        AudioTrack track = manager.player.getPlayingTrack();

        if (!checkUserVoiceChannel(event))
        {
            event.deferEdit().queue();
            return;
        }

        switch (event.getComponentId())
        {
            case "old":
                event.deferEdit().queue();
                if (Main.getDatabase().checkPermission(event.getGuild(), event.getUser(), ICommand.Permission.DJ, event.getTextChannel())) {
                    manager.player.stopTrack();
                    manager.scheduler.nextTrack(manager.scheduler.oldTrack, true);
                } else
                    missingPermission(event);
                break;

            case "pause":
                if (manager.player.isPaused()) {
                    manager.player.setPaused(false);
                } else {
                    manager.player.setPaused(true);
                }

                manager.scheduler.editMessage(event);
                break;
            case "skip":
                event.deferEdit().queue();
                if (Main.getDatabase().checkPermission(event.getGuild(), event.getUser(), ICommand.Permission.DJ, event.getTextChannel())) {
                    manager.player.stopTrack();
                    manager.scheduler.nextTrack(track, false);
                } else
                    missingPermission(event);
                break;
            case "repeat":
                manager.scheduler.repeatPlaylist = !manager.scheduler.repeatPlaylist;
                manager.scheduler.editMessage(event);
                break;
            case "repeatOne":
                manager.scheduler.repeatMusic = !manager.scheduler.repeatMusic;
                manager.scheduler.editMessage(event);
                break;
            case "lyrics":
                if (!manager.scheduler.lyricsAlwaysRequested){
                    StringBuilder builder = new StringBuilder(track.getInfo().title);
                    if (track.getInfo().title.contains("[")) builder.delete(track.getInfo().title.indexOf("["), track.getInfo().title.indexOf("]"));
                    if (track.getInfo().title.contains("(")) builder.delete(track.getInfo().title.indexOf("("), track.getInfo().title.indexOf(")"));

                    Lyrics temp = LyricsAPI.search(builder.toString());

                    event.deferReply().queue(msg -> {
                        if (temp == null)
                            msg.editOriginal("Lyrics not found.").queue();
                        else
                            LyricsCommand.sendLyrics(msg, temp);
                    });

                    manager.scheduler.lyricsAlwaysRequested = true;
                } else {
                    event.deferEdit().queue();
                }
                break;
            case "stop":
                event.deferEdit().queue();

                if (Main.getDatabase().checkPermission(event.getGuild(), event.getUser(), ICommand.Permission.DJ, event.getTextChannel())) {
                    manager.player.stopTrack();
                    manager.scheduler.purgeQueue();
                    manager.scheduler.nextTrack(track, false);
                    manager.scheduler.alwaysStopped = true;
                    manager.scheduler.repeatMusic = false;
                    manager.scheduler.repeatPlaylist = false;
                } else
                    missingPermission(event);

                break;
        }
    }

    private boolean checkUserVoiceChannel(ButtonClickEvent event) {
        if (event.getMember().getVoiceState() == null) return false;
        if (!event.getMember().getVoiceState().inVoiceChannel()) return false;
        return event.getMember().getVoiceState().getChannel() == event.getGuild().getAudioManager().getConnectedChannel();
    }

    /*@Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        if (event.getUser().isBot()) return;
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

            case "\uD83D\uDD01":
                manager.scheduler.repeatPlaylist = !manager.scheduler.repeatPlaylist;
                manager.scheduler.editMessage();
                break;
            case "\uD83D\uDD02": //repeatMusic 🔂
                manager.scheduler.repeatMusic = !manager.scheduler.repeatMusic;
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
                    manager.scheduler.repeatMusic = false;
                    manager.scheduler.repeatPlaylist = false;
                } else
                    missingPermission(event);
                break;
        }

    }*/

    public void deleteReaction(GuildMessageReactionAddEvent event)
    {
        try {
            event.getReaction().removeReaction(event.getUser()).queue();
        } catch (Exception ignored) {}
    }

    public void missingPermission(ButtonClickEvent event)
    {
        event.getTextChannel().sendMessageEmbeds(new EmbedBuilder().setColor(Color.RED).setTitle("Error")
                .setDescription("You don't have the permission to execute this command.")
                .setFooter("Command executed by " + event.getUser().getAsTag()).build())
                .queue(msg -> new Timer().schedule(new MessageTask(msg), 5000)); }

    public void missingPermission(GuildMessageReactionAddEvent event)
    {
        event.getChannel().sendMessageEmbeds(new EmbedBuilder().setColor(Color.RED).setTitle("Error")
                .setDescription("You don't have the permission to execute this command.")
                .setFooter("Command executed by " + event.getUser().getAsTag()).build())
                .queue(msg -> new Timer().schedule(new MessageTask(msg), 5000)); }
}
