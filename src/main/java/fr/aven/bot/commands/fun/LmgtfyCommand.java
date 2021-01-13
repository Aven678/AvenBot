package fr.aven.bot.commands.fun;

import fr.aven.bot.Constants;
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class LmgtfyCommand implements ICommand
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        String link = "https://lmgtfy.com/?q="+ (args.size() == 0 ? "justaven.xyz" : StringUtils.join(args, "%20"));

        event.getChannel().sendMessage("✅ "+event.getAuthor().getAsMention()+" ─> "+link).queue();
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
        return new MessageEmbed.Field("Sends a link to search something", "Usage: `" + Constants.PREFIX + getInvoke() + "` [something]", false);
    }

    @Override
    public String getInvoke() {
        return "lmgtfy";
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
