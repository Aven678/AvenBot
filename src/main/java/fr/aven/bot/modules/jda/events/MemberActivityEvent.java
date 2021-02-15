package fr.aven.bot.modules.jda.events;

import fr.aven.bot.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.List;

public class MemberActivityEvent extends ListenerAdapter
{
    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event)
    {
        String text = Main.getDatabase().getTextJLB(event.getGuild().getId(), "textJoin");
        String channelID = String.valueOf(Main.getDatabase().queryId(event.getGuild(), "announceChannelID"));

        String textFinal = "";

        if (channelID.equalsIgnoreCase("")) return;
        if (text.equalsIgnoreCase("")) return;
        TextChannel textChannel = event.getGuild().getTextChannelById(channelID);
        if (textChannel == null) return;

        textFinal = text.replaceAll("<guild>", event.getGuild().getName()).replaceAll("<member>", event.getUser().getAsMention()).replaceAll("<number>", String.valueOf(event.getGuild().getMembers().size()));

        if (event.getGuild().getSelfMember().hasPermission(textChannel, Permission.MESSAGE_WRITE))
            textChannel.sendMessage(textFinal).queue();

        Role role = Main.getDatabase().getAutoRole(event.getGuild());
        if (role != null)
        {
            if (!event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_ROLES))
                if (!event.getGuild().getSelfMember().hasPermission(Permission.ADMINISTRATOR))
                    return;

            event.getGuild().addRoleToMember(event.getMember(), role).reason("Autorole by AvenBot").queue();
        }

        super.onGuildMemberJoin(event);
    }

    @Override
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event) {
        String text = Main.getDatabase().getTextJLB(event.getGuild().getId(), "textLeave");
        String channelID = String.valueOf(Main.getDatabase().queryId(event.getGuild(), "announceChannelID"));

        String textFinal = "";

        if (channelID.equalsIgnoreCase("")) return;
        if (text.equalsIgnoreCase("")) return;

        if (event.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS)) {
            List<Guild.Ban> bansList = event.getGuild().retrieveBanList().complete();

            for (Guild.Ban ban : bansList)
                if (ban.getUser().getId().equalsIgnoreCase(event.getUser().getId())) return;
        }
        /*event.getGuild().retrieveAuditLogs().type(ActionType.BAN).queue(auditLogEntries -> {
            for (AuditLogEntry auditLogEntry : auditLogEntries)
            {
                if (auditLogEntry.getTargetId() == event.getUser().getId()) return;
            }
        });*/

        textFinal = text.replaceAll("<guild>", event.getGuild().getName()).replaceAll("<member>", event.getUser().getAsTag()).replaceAll("<number>", String.valueOf(event.getGuild().getMembers().size()));

        TextChannel textChannel = event.getGuild().getTextChannelById(channelID);
        if (event.getGuild().getSelfMember().hasPermission(textChannel, Permission.MESSAGE_WRITE))
            textChannel.sendMessage(textFinal).queue();

        super.onGuildMemberRemove(event);
    }

    @Override
    public void onGuildBan(@Nonnull GuildBanEvent event) {
        String text = Main.getDatabase().getTextJLB(event.getGuild().getId(), "textBan");
        String channelID = String.valueOf(Main.getDatabase().queryId(event.getGuild(), "announceChannelID"));

        String textFinal = "";

        if (channelID.equalsIgnoreCase("")) return;
        if (text.equalsIgnoreCase("")) return;

        textFinal = text.replaceAll("<guild>", event.getGuild().getName()).replaceAll("<member>", event.getUser().getAsTag()).replaceAll("<number>", String.valueOf(event.getGuild().getMembers().size()));

        TextChannel textChannel = event.getGuild().getTextChannelById(channelID);
        if (event.getGuild().getSelfMember().hasPermission(textChannel, Permission.MESSAGE_WRITE))
            textChannel.sendMessage(textFinal).queue();

        super.onGuildBan(event);
    }
}
