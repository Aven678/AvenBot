package fr.aven.bot.commands.fun;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class NsfwPictureCommand implements ICommand
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if (!event.getChannel().isNSFW())
        {
            event.getChannel().sendMessage(Main.getDatabase().getTextFor("isNotNSFW", event.getGuild())).queue();
            return;
        }

        event.getChannel().sendMessage(new EmbedBuilder().setImage(Main.getkSoft().getNsfwPicture()).setFooter("Picture by KSoft.SI").build()).queue();
    }

    @Override
    public Type getType() {
        return Type.FUN;
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Sends a NSWF picture (oof)", "Usage: `" + Constants.PREFIX + getInvoke() + "`", false);
    }

    @Override
    public String getInvoke() {
        return "nsfw";
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
