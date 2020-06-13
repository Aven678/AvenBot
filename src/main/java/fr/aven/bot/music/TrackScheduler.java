package fr.aven.bot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import fr.aven.bot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class TrackScheduler extends AudioEventAdapter
{
    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;

    public Map<Integer, AudioTrack> search = new HashMap<>();
    public Map<AudioTrack, Long> usersRequest = new HashMap<>();

    private Guild guild;
    private TextChannel channel;

    public TrackScheduler(AudioPlayer player, Guild guild, TextChannel channel)
    {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
        this.guild = guild;
        this.channel = channel;
    }

    public Set<AudioTrack> getQueuedTracks() {
        return new LinkedHashSet<>(queue);
    }

    public void queue(AudioTrack track)
    {
        if (!player.startTrack(track, true))
        {
            queue.offer(track);
        }
    }

    public BlockingQueue<AudioTrack> getQueue()
    {
        return queue;
    }

    public void shuffleQueue() {
        List<AudioTrack> tQueue = new ArrayList<>(getQueuedTracks());
        AudioTrack current = tQueue.get(0);
        tQueue.remove(0);
        Collections.shuffle(tQueue);
        tQueue.add(0, current);
        purgeQueue();
        queue.addAll(tQueue);
    }

    public void purgeQueue() {
        queue.clear();
    }

    public void nextTrack()
    {
        if (queue.size() == 0)
        {
            player.destroy();
            PlayerManager.getInstance().destroyGuildMusicManager(guild);
            guild.getAudioManager().closeAudioConnection();
            return;
        }
        // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
        // giving null to startTrack, which is a valid argument and will simply stop the player.
        player.startTrack(queue.poll(), false);
    }

    private String getTimestamp(long milis) {
        long seconds = milis / 1000;
        long hours = Math.floorDiv(seconds, 3600);
        seconds = seconds - (hours * 3600);
        long mins = Math.floorDiv(seconds, 60);
        seconds = seconds - (mins * 60);
        return (hours == 0 ? "" : hours + ":") + String.format("%02d", mins) + ":" + String.format("%02d", seconds);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason)
    {
        // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
        if (endReason.mayStartNext)
        {
            nextTrack();
        }

    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        User userRequest = guild.getJDA().getUserById(usersRequest.get(track));

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(Main.getDatabase().getTextFor("music.progress", guild), track.getInfo().uri, guild.getJDA().getSelfUser().getAvatarUrl());
        builder.setColor(guild.getMember(userRequest).getColor());

        builder.addField(track.getInfo().title, "❱ "+Main.getDatabase().getTextFor("music.author", guild)
                +" : "+ track.getInfo().author
                +"\n❱ "+Main.getDatabase().getTextFor("music.duration", guild)+" : "+ getTimestamp(track.getInfo().length), false);

        builder.setThumbnail("https://i.ytimg.com/vi/" + track.getInfo().identifier + "/hqdefault.jpg");

        builder.setFooter(Main.getDatabase().getTextFor("music.request", guild)+userRequest.getName(), userRequest.getAvatarUrl());

        channel.sendMessage(builder.build()).queue();
    }
}
