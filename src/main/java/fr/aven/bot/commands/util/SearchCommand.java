package fr.aven.bot.commands.util;

import fr.aven.bot.Constants;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SearchCommand implements ICommand
{
    @Override
    public void handle(List<String> args, CommandEvent event) {
        String link = "https://www.google.fr/search?q="+ (args.size() == 0 ? "justaven.xyz" : StringUtils.join(args, "%20"));

        event.getChannel().sendMessage("✅ "+event.getAuthor().getAsMention()+" ─> "+link).queue();
    }

    @Override
    public Type getType() {
        return Type.UTIL;
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Sends a link to search something", "Usage: `" + Constants.PREFIX + getInvoke() + "` [something]", false);
    }

    @Override
    public String getInvoke() {
        return "search";
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
        return Arrays.asList(net.dv8tion.jda.api.Permission.MESSAGE_EMBED_LINKS, net.dv8tion.jda.api.Permission.MESSAGE_WRITE);
    }
}
