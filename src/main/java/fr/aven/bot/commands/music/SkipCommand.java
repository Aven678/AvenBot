package fr.aven.bot.commands.music;

import fr.aven.bot.Constants;
import fr.aven.bot.music.PlayerManager;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class SkipCommand extends MusicCommands
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event)
    {
        PlayerManager manager = PlayerManager.getInstance();
        manager.getGuildMusicManager(event.getGuild(), event.getChannel()).player.stopTrack();
        manager.getGuildMusicManager(event.getGuild(), event.getChannel()).scheduler.nextTrack();

        event.getChannel().sendMessage("Track skipped !").queue();
    }

    @Override
    public MessageEmbed.Field getHelp()
    {
        return new MessageEmbed.Field("Skips a song", "Usage: `" + Constants.PREFIX + getInvoke() , false);
    }

    @Override
    public String getInvoke()
    {
        return "skip";
    }

    @Override
    public Permission getPermission()
    {
        return Permission.DJ;
    }
}
