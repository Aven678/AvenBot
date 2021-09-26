package fr.aven.bot.commands.music;

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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PauseCommand implements ICommand
{
    @Override
    public void handle(List<String> args, CommandEvent event) {
        if (!event.getGuild().getAudioManager().isConnected()) {
            event.getChannel().sendMessageEmbeds(new EmbedBuilder()
                    .setTitle(Main.getLanguage().getTextFor("argsNotFound", event.getGuild()))
                    .setDescription(Main.getLanguage().getTextFor("pause.notConnected", event.getGuild()))
                    .build()).queue();

            return;
        }

        TextChannel channel = event.getChannel();
        PlayerManager manager = PlayerManager.getInstance();
        GuildMusicManager musicManager = manager.getGuildMusicManager(event.getGuild(), event.getChannel());

        if (!musicManager.player.isPaused()) {
            manager.getGuildMusicManager(event.getGuild(), event.getChannel()).player.setPaused(true);
            manager.getGuildMusicManager(event.getGuild(), event.getChannel()).scheduler.editMessage();

            event.getChannel().sendMessageEmbeds(new EmbedBuilder()
                    .setTitle(Main.getLanguage().getTextFor("success", event.getGuild()))
                    .setDescription(Main.getLanguage().getTextFor("pause.playerPaused", event.getGuild()))
                    .build()).queue();
        } else {
            manager.getGuildMusicManager(event.getGuild(), event.getChannel()).scheduler.editMessage();
            manager.getGuildMusicManager(event.getGuild(), event.getChannel()).player.setPaused(false);

            event.getChannel().sendMessageEmbeds(new EmbedBuilder()
                    .setTitle(Main.getLanguage().getTextFor("success", event.getGuild()))
                    .setDescription(Main.getLanguage().getTextFor("pause.playerResume", event.getGuild()))
                    .build()).queue();
        }
    }

    @Override
    public Type getType() {
        return Type.MUSIC;
    }

    @Override
    public Permission getPermission() {
        return Permission.DJ;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Pause the player.", "Usage: `" + Constants.PREFIX + getInvoke() + "`", false);
    }

    @Override
    public String getInvoke() {
        return "pause";
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
