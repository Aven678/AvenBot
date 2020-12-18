package fr.aven.bot.modules.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class GuildMusicManager
{
    public final AudioPlayer player;

    public final TrackScheduler scheduler;

    public GuildMusicManager(AudioPlayerManager manager, Guild guild, TextChannel channel)
    {
        player = manager.createPlayer();
        scheduler = new TrackScheduler(player, guild, channel);
        player.addListener(scheduler);
    }

    public AudioPlayerSendHandler getSendHandler()
    {
        return new AudioPlayerSendHandler(player);
    }
}
