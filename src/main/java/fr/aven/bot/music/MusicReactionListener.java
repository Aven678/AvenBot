package fr.aven.bot.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import fr.aven.bot.Main;
import fr.aven.bot.commands.music.LyricsCommand;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class MusicReactionListener extends ListenerAdapter
{
    private List<String> emotes = Arrays.asList("⏮️","⏯️", "⏭️", "\uD83D\uDD01", "\uD83D\uDCDC", "❌");

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        if (event.getUser().isBot()) return;
        if (event.getGuild() == null) return;
        GuildMusicManager manager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild(), event.getChannel());

        if (event.getMessageIdLong() != manager.scheduler.lastMessageStatus) {
            deleteReaction(event);
            return;
        }

        if (!emotes.contains(event.getReactionEmote().getName())) {
            deleteReaction(event);
            return;
        }

        AudioTrack track = manager.player.getPlayingTrack();
        switch (event.getReactionEmote().getName())
        {
            case "⏮️": //old
                manager.player.stopTrack();
                manager.scheduler.nextTrack(manager.scheduler.oldTrack, true);
                break;
            case "⏯️": //playpause
                if (manager.player.isPaused()) {
                    manager.player.setPaused(false);
                } else {
                    manager.player.setPaused(true);
                }
                break;
            case "⏭️": //skip
                manager.player.stopTrack();
                manager.scheduler.nextTrack(track, false);
                break;
            case "\uD83D\uDD01": //repeat
                manager.scheduler.repeat = true;
                break;
            case "\uD83D\uDCDC": //lyrics
                LyricsCommand.sendLyrics(event, Main.getkSoft().getLyrics(track.getInfo().title));
                break;
            case "❌": //stop
                manager.player.stopTrack();
                manager.scheduler.purgeQueue();
                manager.scheduler.nextTrack(track, false);
                event.getChannel().sendMessage(Main.getDatabase().getTextFor("stop.confirm", event.getGuild())).queue();
                break;
        }

        deleteReaction(event);

    }

    public void deleteReaction(GuildMessageReactionAddEvent event)
    {
        event.getReaction().removeReaction(event.getUser()).queue();
    }
}
