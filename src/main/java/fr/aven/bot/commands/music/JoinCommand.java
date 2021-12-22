package fr.aven.bot.commands.music;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.modules.core.CommandEvent;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class JoinCommand extends MusicCommands
{
    @Override
    public void handle(List<String> args, CommandEvent event)
    {
        TextChannel channel = event.getChannel();
        joinChannel(event);
        channel.sendMessage(Main.getLanguage().getTextFor("join.success", event.getGuild())).queue();
    }

    public static boolean joinChannel(CommandEvent event)
    {
        TextChannel channel = event.getChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();
        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

        if (!memberVoiceState.inVoiceChannel())
        {
            channel.sendMessage(Main.getLanguage().getTextFor("join.isNotInChannel", event.getGuild())).queue();
            return false;
        }

        VoiceChannel voiceChannel = memberVoiceState.getChannel();
        Member selfMember = event.getGuild().getSelfMember();

        if (!selfMember.hasPermission(voiceChannel, net.dv8tion.jda.api.Permission.VOICE_CONNECT))
        {
            channel.sendMessageFormat(Main.getLanguage().getTextFor("join.missingPerm", event.getGuild()), voiceChannel).queue();
            return false;
        }

        audioManager.openAudioConnection(voiceChannel);
        audioManager.setSelfDeafened(true);

        return true;
    }

    @Override
    public MessageEmbed.Field getHelp()
    {
        return new MessageEmbed.Field(getDescription(), "Usage: `" + Constants.PREFIX + getInvoke() + "`", false);
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
        return "join";
    }

    @Override
    public String getDescription() {
        return "Makes the bot join your channel";
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

    @Override
    public Collection<net.dv8tion.jda.api.Permission> requiredDiscordPermission() {
        return Arrays.asList(net.dv8tion.jda.api.Permission.VOICE_CONNECT, net.dv8tion.jda.api.Permission.MESSAGE_WRITE);
    }
}
