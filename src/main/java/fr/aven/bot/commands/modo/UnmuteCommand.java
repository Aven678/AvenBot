package fr.aven.bot.commands.modo;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.modules.core.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UnmuteCommand extends ModoCommands {

    @Override
    public void handle(List<String> args, CommandEvent event) {
        TextChannel channel = event.getChannel();
        Message message = event.message();
        Guild guild = event.getGuild();

        if (!guild.getSelfMember().hasPermission(net.dv8tion.jda.api.Permission.MANAGE_ROLES))
        {
            channel.sendMessageEmbeds(new EmbedBuilder()
                    .setTitle(Main.getLanguage().getTextFor("error", event.getGuild()))
                    .setDescription(Main.getLanguage().getTextFor("hasNotPermission", event.getGuild()))
                    .setColor(Color.RED)
                    .setFooter("Command executed by "+event.getAuthor().getAsTag())
                    .build()
            ).queue();
            return;
        }

        Role mutedRole = guild.getRolesByName("Muted", true).get(0);
        if (args.isEmpty()) {
            channel.sendMessage(Main.getLanguage().getTextFor("argsNotFound", event.getGuild())).queue();

            return;
        } else if (message.getMentionedUsers().isEmpty()) {
            channel.sendMessage(Main.getLanguage().getTextFor("unmute.notMentionned", event.getGuild())).queue();

            return;
        } else if (!message.getMentionedMembers().get(0).getRoles().contains(mutedRole)) {
            channel.sendMessage(Main.getLanguage().getTextFor("unmute.isNotMute", event.getGuild())).queue();

            return;
        }


        guild.removeRoleFromMember(message.getMentionedMembers().get(0), mutedRole).queue();

        MODOLOGGER.info(event.getAuthor().getName() + " unmuted " + message.getMentionedMembers().get(0).getEffectiveName());

        channel.sendMessage(Main.getLanguage().getTextFor("unmute.confirm", event.getGuild()) + " : "+message.getMentionedMembers().get(0).getUser().getAsTag()).queue();

    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field(getDescription(), "Usage: `" + Constants.PREFIX + getInvoke() + " <@user>`", false);
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
    public String getDescription() {
        return "Unmute a member";
    }

    @Override
    public Type getType() {
        return super.getType();
    }

    @Override
    public Collection<net.dv8tion.jda.api.Permission> requiredDiscordPermission() {
        return Arrays.asList(net.dv8tion.jda.api.Permission.MANAGE_ROLES, net.dv8tion.jda.api.Permission.MESSAGE_EMBED_LINKS);
    }
}
