package fr.aven.bot.commands.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.music.PlayerManager;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SkipCommand extends MusicCommands
{
    @Override
    public void handle(List<String> args, CommandEvent event)
    {
        PlayerManager manager = PlayerManager.getInstance();
        AudioTrack track = manager.getGuildMusicManager(event.getGuild(), event.getChannel()).player.getPlayingTrack();
        manager.getGuildMusicManager(event.getGuild(), event.getChannel()).player.stopTrack();
        manager.getGuildMusicManager(event.getGuild(), event.getChannel()).scheduler.nextTrack(track, false);

        event.getChannel().sendMessage(Main.getLanguage().getTextFor("skip.confirm", event.getGuild())).queue();
    }

    @Override
    public MessageEmbed.Field getHelp()
    {
        return new MessageEmbed.Field(getDescription(), "Usage: `" + Constants.PREFIX + getInvoke() , false);
    }

    @Override
    public String getInvoke()
    {
        return "skip";
    }

    @Override
    public String getDescription() {
        return "Skips a song";
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
