package fr.aven.bot.commands.music;

import fr.aven.bot.Constants;
import fr.aven.bot.music.PlayerManager;
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class VolumeCommand  implements ICommand
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if (args.isEmpty())
        {
            event.getChannel().sendMessage(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        try {
            int volume = Integer.parseInt(args.get(0));

            if (volume > 200)
            {
                event.getChannel().sendMessage("Volume cannot exceed 200.").queue();
                return;
            }

            PlayerManager.getInstance().getGuildMusicManager(event.getGuild(), event.getChannel()).player.setVolume(volume);
            event.getChannel().sendMessage("The volume has been "+volume+" now.").queue();
        } catch (NumberFormatException nfe)
        {
            event.getChannel().sendMessage(new EmbedBuilder().addField(getHelp()).build()).queue();
        }
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
        return new MessageEmbed.Field("Set the player volume.", "Usage: `" + Constants.PREFIX + getInvoke() + "`", false);
    }

    @Override
    public String getInvoke() {
        return "volume";
    }

    @Override
    public boolean haveEvent() {
        return false;
    }

    @Override
    public void onEvent(GenericEvent event) {

    }
}
