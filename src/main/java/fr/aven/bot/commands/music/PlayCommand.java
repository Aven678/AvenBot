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
        }

        TextChannel channel = event.getChannel();
        PlayerManager manager = PlayerManager.getInstance();

        if (args.isEmpty())
        {
            if (manager.getGuildMusicManager(event.getGuild(), event.getChannel()).scheduler.getQueue().isEmpty())
                channel.sendMessage(Main.getDatabase().getTextFor("argsNotFound", event.getGuild())).queue();

            return;
        }

        if (!PlayerManager.getInstance().getGuildMusicManager(event.getGuild(), event.getChannel()).scheduler.search.isEmpty())
        {
            channel.sendMessage(Main.getDatabase().getTextFor("play.confirmChoice", event.getGuild())).queue();
            return;
        }

        String input = String.join(" ", args);

        if (!isUrl(input))
        {
            sendMessage(input, event);
        } else {
            manager.loadAndPlay(event.getMessage(), input);
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

    public void sendMessage(String input, GuildMessageReceivedEvent event)
    {
        Iterator<SearchResult> search = PlayerManager.getInstance().getYoutubeAPI().search(input);
        int nbTrack = 1;

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(Main.getDatabase().getTextFor("music.searchTitle", event.getGuild()), "https://justaven.com", event.getAuthor().getAvatarUrl());
        builder.setColor(event.getMember().getColor());
        builder.setFooter(Main.getDatabase().getTextFor("music.searchFooter", event.getGuild()), event.getJDA().getSelfUser().getAvatarUrl());

        GuildMusicManager musicManager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild(), event.getChannel());
        while (search.hasNext()) {
            SearchResult singleVideo = search.next();
            System.out.println(singleVideo.getSnippet().getTitle());

            musicManager.scheduler.search.put(nbTrack, singleVideo);
            builder.appendDescription("\n`" + nbTrack + "`: **" + singleVideo.getSnippet().getTitle() + "** | "+Main.getDatabase().getTextFor("music.author", event.getGuild())+" : " + singleVideo.getSnippet().getChannelTitle());

            nbTrack++;
        }


        event.getChannel().sendMessage(builder.build()).queue(msg -> msg.addReaction("❌").queue());
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