package fr.aven.bot.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.music.PlayerManager;
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class QueueCommand implements ICommand
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();

        if (PlayerManager.getInstance().checkNullForEvent(event.getGuild()))
        {
            channel.sendMessage(Main.getDatabase().getTextFor("queue.playerNotActive", event.getGuild())).queue();
            return;
        }

        BlockingQueue<AudioTrack> queue = PlayerManager.getInstance().getGuildMusicManager(event.getGuild(), channel).scheduler.getQueue();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("Queue", "https://justaven.com", event.getJDA().getSelfUser().getAvatarUrl());
        builder.setFooter(Main.getDatabase().getTextFor("music.request", event.getGuild()) + event.getAuthor().getName());

        for (AudioTrack track : queue)
        {
            AudioTrackInfo info = track.getInfo();
            builder.appendDescription("\n[" + getTimestamp(info.length) + "] **" + info.title+ "** | *" + info.author + "*");
        }

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
        return new MessageEmbed.Field("Show the queue of the player.", "`Usage: "+ Constants.PREFIX + getInvoke()+"`", false);
    }

    @Override
    public String getInvoke() {
        return "queue";
    }

    @Override
    public boolean haveEvent() {
        return false;
    }

    @Override
    public void onEvent(GenericEvent event) {

    }
}
