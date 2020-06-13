package fr.aven.bot.commands.music;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.music.PlayerManager;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.List;

public class StopCommand extends MusicCommands
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event)
    {
        TextChannel channel = event.getChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();

        if (!audioManager.isConnected())
        {
            channel.sendMessage(Main.getDatabase().getTextFor("stop.botNotConnected", event.getGuild())).queue();
            return;
        }

        VoiceChannel voiceChannel = audioManager.getConnectedChannel();

        if (!voiceChannel.getMembers().contains(event.getMember()))
        {
            channel.sendMessage(Main.getDatabase().getTextFor("stop.isNotInSameChannel", event.getGuild())).queue();
            return;
        }

        PlayerManager manager = PlayerManager.getInstance();
        manager.getGuildMusicManager(event.getGuild(), event.getChannel()).player.stopTrack();
        manager.getGuildMusicManager(event.getGuild(), event.getChannel()).scheduler.getQueue().clear();
        manager.getGuildMusicManager(event.getGuild(), event.getChannel()).scheduler.nextTrack();

        channel.sendMessage(Main.getDatabase().getTextFor("stop.confirm", event.getGuild())).queue();
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
}