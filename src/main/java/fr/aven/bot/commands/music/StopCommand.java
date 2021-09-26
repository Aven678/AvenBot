package fr.aven.bot.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.music.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class StopCommand extends MusicCommands
{
    @Override
    public void handle(List<String> args, CommandEvent event)
    {
        TextChannel channel = event.getChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();

        if (!audioManager.isConnected())
        {
            channel.sendMessage(Main.getLanguage().getTextFor("stop.botNotConnected", event.getGuild())).queue();
            return;
        }

        VoiceChannel voiceChannel = audioManager.getConnectedChannel();

        if (!voiceChannel.getMembers().contains(event.getMember()))
        {
            channel.sendMessage(Main.getLanguage().getTextFor("stop.isNotInSameChannel", event.getGuild())).queue();
            return;
        }

        stop(event.getGuild(), channel);
    }

    public static void stop(Guild guild, TextChannel channel)
    {
        PlayerManager manager = PlayerManager.getInstance();
        AudioTrack track = manager.getGuildMusicManager(guild, channel).player.getPlayingTrack();

        manager.getGuildMusicManager(guild, channel).scheduler.alwaysStopped = true;
        manager.getGuildMusicManager(guild, channel).player.stopTrack();
        manager.getGuildMusicManager(guild, channel).scheduler.getQueue().clear();
        manager.getGuildMusicManager(guild, channel).scheduler.nextTrack(track, false);

        //manager.getGuildMusicManager(guild, channel).scheduler.channel.sendMessage(Main.getDatabase().getTextFor("stop.confirm", guild)).queue();
    }

    @Override
    public MessageEmbed.Field getHelp()
    {
        return new MessageEmbed.Field("Bot leaves the voice channel and stops track", "Usage: `" + Constants.PREFIX + getInvoke() + "`", false);
    }

    @Override
    public boolean haveEvent() {
        return false;
    }

    @Override
    public void onEvent(GenericEvent event) {}

    @Override
    public String getInvoke()
    {
        return "stop";
    }

    @Override
    public Type getType() {
        return super.getType();
    }

    @Override
    public Permission getPermission()
    {
        return Permission.DJ;
    }

    @Override
    public Collection<net.dv8tion.jda.api.Permission> requiredDiscordPermission() {
        return Arrays.asList(net.dv8tion.jda.api.Permission.MESSAGE_WRITE, net.dv8tion.jda.api.Permission.MESSAGE_EMBED_LINKS);
    }
}