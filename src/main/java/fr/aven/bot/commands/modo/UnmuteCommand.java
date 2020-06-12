package fr.aven.bot.commands.modo;

import fr.aven.bot.Constants;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class UnmuteCommand extends ModoCommands {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        Message message = event.getMessage();
        Guild guild = event.getGuild();

        Role mutedRole = guild.getRolesByName("Muted", true).get(0);
        if (args.isEmpty()) {
            channel.sendMessage("Please provide some arguments").queue();

            return;
        } else if (message.getMentionedUsers().isEmpty()) {
            channel.sendMessage("Please provide a member to unmute").queue();

            return;
        } else if (!message.getMentionedMembers().get(0).getRoles().contains(mutedRole)) {
            channel.sendMessage("Please provide a muted member").queue();

            return;
        }

        guild.removeRoleFromMember(message.getMentionedMembers().get(0), mutedRole).queue();

        MODOLOGGER.info(event.getAuthor().getName() + " unmuted " + message.getMentionedMembers().get(0).getEffectiveName());

    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Unmute a member", "Usage: `" + Constants.PREFIX + getInvoke() + " <@user>`", false);
    }

    @Override
    public boolean haveEvent() {
        return false;
    }

    @Override
    public void onEvent(GenericEvent event) {}

    @Override
    public String getInvoke() {
        return "unmute";
    }

    @Override
    public Type getType() {
        return super.getType();
    }
}
