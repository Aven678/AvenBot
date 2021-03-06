package fr.aven.bot.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.music.GuildMusicManager;
import fr.aven.bot.modules.music.PlayerManager;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.*;

public class QueueCommand implements ICommand
{
    @Override
    public void handle(List<String> args, CommandEvent event) {
        int SideNumbInput = 1;
        if (args.size() > 0)
            SideNumbInput = Integer.parseInt(args.get(0));

        TextChannel channel = event.getChannel();
        GuildMusicManager manager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild(), channel);
        StringBuilder sb = new StringBuilder();
        sb.append("**"+Main.getLanguage().getTextFor("music.progress", event.getGuild())+" ** ❱ `["+getTimestamp(manager.player.getPlayingTrack().getInfo().length)+"]` "+manager.player.getPlayingTrack().getInfo().title);

        if (PlayerManager.getInstance().getGuildMusicManager(event.getGuild(), event.getChannel()).scheduler.getQueue().isEmpty())
        {
            channel.sendMessage(Main.getLanguage().getTextFor("queue.playerNotActive", event.getGuild())).queue();
            return;
        }

        Set<AudioTrack> queue = manager.scheduler.getQueuedTracks();
        ArrayList<String> tracks = new ArrayList<>();
        queue.forEach(track -> tracks.add("\n`[" + getTimestamp(track.getInfo().length) + "]` " + track.getInfo().title));


        List<String> tracksSublist;

        if (queue.size() > 20)
            tracksSublist = tracks.subList((SideNumbInput - 1) * 20, (SideNumbInput - 1) * 20 + 20);
        else
            tracksSublist = tracks;

        int sideNumbAll = tracks.size() >= 20 ? tracks.size() / 20 : 1;
        int sideNumb = SideNumbInput;
        tracksSublist.forEach(s -> sb.append(s));

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("Queue : "+queue.size()+" tracks", "https://justaven.xyz", event.getJDA().getSelfUser().getAvatarUrl());

        builder.setDescription(sb);
        builder.setColor(event.getMember().getColor());
        builder.setFooter("Page "+ sideNumb + "/" + sideNumbAll);
        channel.sendMessage(builder.build()).queue();
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
    public Type getType() {
        return Type.MUSIC;
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field(getDescription(), "`Usage: "+ Constants.PREFIX + getInvoke()+"`", false);
    }

    @Override
    public String getInvoke() {
        return "queue";
    }

    @Override
    public String getDescription() {
        return "Show the queue of the player.";
    }

    @Override
    public boolean haveEvent() {
        return false;
    }

    @Override
    public void onEvent(GenericEvent event) {

    }

    @Override
    public Collection<net.dv8tion.jda.api.Permission> requiredDiscordPermission() {
        return Arrays.asList(net.dv8tion.jda.api.Permission.MESSAGE_WRITE, net.dv8tion.jda.api.Permission.MESSAGE_EMBED_LINKS);
    }
}
