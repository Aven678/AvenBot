package fr.aven.bot.commands.music;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class BassCommand extends MusicCommands
{
    @Override
    public void handle(List<String> args, CommandEvent event) {
        var channel = event.getChannel();

        if (args.isEmpty())
        {
            channel.sendMessageEmbeds(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        var audioManager = event.getGuild().getAudioManager();
        var manager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild(), event.getChannel());

        if (!audioManager.isConnected())
        {
            channel.sendMessage(Main.getLanguage().getTextFor("stop.botNotConnected", event.getGuild())).queue();
            return;
        }

        var voiceChannel = audioManager.getConnectedChannel();

        if (!voiceChannel.getMembers().contains(event.getMember()))
        {
            channel.sendMessage(Main.getLanguage().getTextFor("stop.isNotInSameChannel", event.getGuild())).queue();
            return;
        }

        try {
            var choice = Integer.parseInt(args.get(0));
            manager.scheduler.bassBoost(choice, event.message());
        } catch (NumberFormatException nfe)
        {
            channel.sendMessageEmbeds(new EmbedBuilder().addField(getHelp()).build()).queue();
        }
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field(getDescription(), "Usage: `" + Constants.PREFIX + getInvoke() + "` <percentage> \nExample: "+Constants.PREFIX + getInvoke() +" 20", false);
    }

    @Override
    public String getInvoke() {
        return "bass";
    }

    @Override
    public String getDescription() {
        return "Boost the bass.";
    }

    @Override
    public Collection<net.dv8tion.jda.api.Permission> requiredDiscordPermission() {
        return Arrays.asList(net.dv8tion.jda.api.Permission.MESSAGE_WRITE, net.dv8tion.jda.api.Permission.MESSAGE_EMBED_LINKS);
    }

    @Override
    public Permission getPermission() {
        return Permission.DJ;
    }
}
