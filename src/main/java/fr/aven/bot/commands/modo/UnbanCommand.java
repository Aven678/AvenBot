package fr.aven.bot.commands.modo;

import fr.aven.bot.Constants;
import fr.aven.bot.modules.core.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UnbanCommand extends ModoCommands
{
    @Override
    public void handle(List<String> args, CommandEvent event) {
        if (args.isEmpty())
        {
            event.getChannel().sendMessageEmbeds(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        String id = args.get(0);

        List<Guild.Ban> bansList = event.getGuild().retrieveBanList().complete();

        for (Guild.Ban ban : bansList)
        {
            if (ban.getUser().getId().equalsIgnoreCase(id))
            {
                event.getGuild().unban(args.get(0)).queue();
                break;
            }
        }
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Unban a user", "Usage: `" + Constants.PREFIX + getInvoke() + " <id>`", false);
    }

    @Override
    public String getInvoke() {
        return "unban";
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
        return Arrays.asList(net.dv8tion.jda.api.Permission.BAN_MEMBERS, net.dv8tion.jda.api.Permission.MESSAGE_EMBED_LINKS, net.dv8tion.jda.api.Permission.MESSAGE_WRITE);
    }
}
