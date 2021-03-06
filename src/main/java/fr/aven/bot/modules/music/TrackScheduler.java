package fr.aven.bot.modules.music;

import com.sedmelluq.discord.lavaplayer.filter.equalizer.EqualizerFactory;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import fr.aven.bot.Main;
import fr.aven.bot.modules.music.lyrics.Lyrics;
import fr.aven.bot.util.MessageTask;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;
import net.explodingbush.ksoftapi.entities.Lyric;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class TrackScheduler extends AudioEventAdapter
{
    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;

    public Map<Integer, AudioTrack> search = new HashMap<>();
    public Map<Integer, Lyrics> lyrics = new HashMap<>();

    public Map<AudioTrack, Long> usersRequest = new HashMap<>();
    public boolean lyricsAlwaysRequested = false;
    public boolean alwaysStopped = false;

    private Guild guild;
    public TextChannel channel;

    public boolean repeatMusic = false;
    public boolean repeatPlaylist = false;
    private boolean oldUsed = false;

    public AudioTrack oldTrack = null;

    public Message lastMessageStatus = null;
    public Message lastMessageLyrics = null;
    public Message lastMessageSearch = null;

    public int boostPercentage = 0;
    private static final float[] BASS_BOOST = {0.2f, 0.15f, 0.1f, 0.05f, 0.0f, -0.05f, -0.1f, -0.1f, -0.1f, -0.1f, -0.1f,
            -0.1f, -0.1f, -0.1f, -0.1f};
    public EqualizerFactory equalizer = new EqualizerFactory();

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
        } else {
            if (player.isPaused()) player.setPaused(false);
            player.setVolume(100);
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

    public void bassBoost(int diff, Message message) {
        final int previousPercentage = this.boostPercentage;
        this.boostPercentage = diff;

        // Disable filter factory
        if (previousPercentage > 0 && diff == 0) {
            this.player.setFilterFactory(null);
            message.addReaction("???").queue();
            return;
        }
        // Enable filter factory
        if (previousPercentage == 0 && diff > 0) {
            this.player.setFilterFactory(this.equalizer);
        }

        for (int i = 0; i < BASS_BOOST.length; i++) {
            this.equalizer.setGain(i, BASS_BOOST[i] + diff);
        }

        for (int i = 0; i < BASS_BOOST.length; i++) {
            equalizer.setGain(i, -BASS_BOOST[i] + diff);
        }

        this.boostPercentage = diff;
        message.addReaction("???").queue();
    }

    public void purgeQueue() {
        queue.clear();
    }

    public void nextTrack(AudioTrack track, boolean oldMusicRequested)
    {
        lyricsAlwaysRequested = false;

        if (oldMusicRequested && track != null && !oldUsed)
        {
            AudioTrack track1 = oldTrack.makeClone();
            usersRequest.put(track1, usersRequest.get(track));
            player.startTrack(track1, false);
            oldUsed = true;
            return;
        }

        if (repeatMusic)
        {
            AudioTrack track1 = track.makeClone();
            usersRequest.put(track1, usersRequest.get(track));
            player.startTrack(track1, false);
            repeatMusic = false;
            return;
        }

        if (repeatPlaylist)
        {
            AudioTrack track1 = track.makeClone();
            usersRequest.put(track1, usersRequest.get(track));
            queue.offer(track1);
        }

        if (queue.size() == 0)
        {
            alwaysStopped = true;
            repeatPlaylist = false;
            repeatMusic = false;
            //player.destroy();
            //PlayerManager.getInstance().destroyGuildMusicManager(guild);

            //Disconnect the voice channel after 30 sec
            new Timer().schedule(new DisconnectTask(),30000);
            return;
        }
        // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
        // giving null to startTrack, which is a valid argument and will simply stop the player.
        oldTrack = track;
        oldUsed = false;

        player.setPaused(false);
        player.startTrack(queue.poll(), false);
    }

    private class DisconnectTask extends TimerTask
    {
        @Override
        public void run() {
            if (player.getPlayingTrack() != null) return;

            channel.sendMessage(Main.getLanguage().getTextFor("stop.confirm", guild)).queue(msg -> new Timer().schedule(new MessageTask(msg), 10000));
            if (lastMessageStatus != null) lastMessageStatus.delete().queue();
            guild.getAudioManager().closeAudioConnection();
            boostPercentage = 0;
            player.setFilterFactory(null);

        }
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
            nextTrack(track, false);
        }

    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {

        alwaysStopped = false;

        if (guild.getTextChannelById(channel.getId()) == null) return;
        if (lastMessageStatus != null) lastMessageStatus.delete().queue(success -> {}, error -> {});

        String repeatPlaylistField = "";

        User userRequest = guild.getJDA().getUserById(usersRequest.get(track));

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(Main.getLanguage().getTextFor("music.progress", guild), track.getInfo().uri, guild.getJDA().getSelfUser().getAvatarUrl());
        builder.setColor(guild.getMember(userRequest).getColor());

        if (repeatPlaylist)
            repeatPlaylistField = "\n??? " + Main.getLanguage().getTextFor("music.repeatPlaylistRequested", guild);

        builder.addField(track.getInfo().title, "??? "+Main.getLanguage().getTextFor("music.author", guild)
                +" : "+ track.getInfo().author
                +"\n??? "+Main.getLanguage().getTextFor("music.duration", guild)+" : "+ getTimestamp(track.getInfo().length) + repeatPlaylistField, false);

        builder.setThumbnail("https://i.ytimg.com/vi/" + track.getInfo().identifier + "/maxresdefault.jpg");

        builder.setFooter(Main.getLanguage().getTextFor("music.request", guild)+userRequest.getName(), userRequest.getAvatarUrl());

        channel.sendMessageEmbeds(builder.build()).setActionRows(ActionRow.of(
                Button.secondary("old","Back").withEmoji(Emoji.fromUnicode("??????")),
                Button.secondary("pause","Pause").withEmoji(Emoji.fromUnicode("??????")),
                Button.secondary("skip", "Skip").withEmoji(Emoji.fromUnicode("??????")),
                Button.secondary("repeat", "Repeat").withEmoji(Emoji.fromUnicode("\uD83D\uDD01"))),

                ActionRow.of(
                        Button.secondary("repeatOne","Repeat track").withEmoji(Emoji.fromUnicode("\uD83D\uDD02")),
                        Button.secondary("lyrics", "Lyrics").withEmoji(Emoji.fromUnicode("\uD83D\uDCDC")),
                        Button.danger("stop", "Stop").withEmoji(Emoji.fromUnicode("???")))
        )

                .queue(msg -> {

            lastMessageStatus = msg;
        }, error -> {});
    }

    public void editMessage(ButtonClickEvent event)
    {
        modifyMessage(event);
    }

    public void editMessage()
    {
        modifyMessage(null);
    }

    private void modifyMessage(ButtonClickEvent event)
    {
        AudioTrack track = PlayerManager.getInstance().getGuildMusicManager(guild, channel).player.getPlayingTrack();
        if (track == null) return;

        User userRequest = guild.getJDA().getUserById(usersRequest.get(track));

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(Main.getLanguage().getTextFor(player.isPaused() ? "player.paused" : "music.progress", guild), track.getInfo().uri, guild.getJDA().getSelfUser().getAvatarUrl());
        builder.setColor(guild.getMember(userRequest).getColor());
        String repeatMusicField = "";
        String repeatPlaylistField = "";

        if (repeatMusic)
            repeatMusicField = "\n??? "+ Main.getLanguage().getTextFor("music.repeatRequested", guild);

        if (repeatPlaylist)
            repeatPlaylistField = "\n??? " + Main.getLanguage().getTextFor("music.repeatPlaylistRequested", guild);

        builder.addField(track.getInfo().title, "??? "+Main.getLanguage().getTextFor("music.author", guild)
                +" : "+ track.getInfo().author
                +"\n??? "+Main.getLanguage().getTextFor("music.duration", guild)+" : "+ getTimestamp(track.getInfo().length) + repeatPlaylistField + repeatMusicField, false);

        builder.setThumbnail("https://i.ytimg.com/vi/" + track.getInfo().identifier + "/maxresdefault.jpg");

        builder.setFooter(Main.getLanguage().getTextFor("music.request", guild)+userRequest.getName(), userRequest.getAvatarUrl());

        List<ActionRow> actionRowCollections = Arrays.asList(ActionRow.of(
                Button.secondary("old","Back").withEmoji(Emoji.fromUnicode("??????")),
                Button.secondary("pause",(player.isPaused() ? "Reprendre" : "Pause")).withEmoji(Emoji.fromUnicode(player.isPaused() ? "??????" : "??????")),
                Button.secondary("skip", "Skip").withEmoji(Emoji.fromUnicode("??????")),
                Button.secondary("repeat", "Repeat").withEmoji(Emoji.fromUnicode("\uD83D\uDD01"))),

                ActionRow.of(
                        Button.secondary("repeatOne","Repeat track").withEmoji(Emoji.fromUnicode("\uD83D\uDD02")),
                        Button.secondary("lyrics", "Lyrics").withEmoji(Emoji.fromUnicode("\uD83D\uDCDC")),
                        Button.danger("stop", "Stop").withEmoji(Emoji.fromUnicode("???"))));

        if (event == null) lastMessageStatus.editMessage(new MessageBuilder().setEmbeds(builder.build()).build()).setActionRows(actionRowCollections).queue();
        else event.editMessageEmbeds(builder.build()).setActionRows(actionRowCollections).queue();
    }

    public void clearLyricsMap()
    {
        lyrics.clear();
    }

    public void putLyricsMap(Integer choice, Lyrics value)
    {
        lyrics.put(choice, value);
    }

    public Lyrics getLyrics(Integer choice)
    {
        return lyrics.get(choice);
    }
}
