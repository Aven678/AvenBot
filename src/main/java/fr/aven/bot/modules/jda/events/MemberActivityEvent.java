package fr.aven.bot.modules.jda.events;

import fr.aven.bot.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdatePendingEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

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

        //Autorole
        Member selfMember = event.getGuild().getSelfMember();
        Role role = Main.getDatabase().getAutoRole(event.getGuild());
        if (role != null)
            if (!event.getMember().isPending())
                if (selfMember.canInteract(role) && selfMember.hasPermission(Permission.MANAGE_ROLES)) event.getGuild().addRoleToMember(event.getMember(), role).reason("Autorole by AvenBot").queue();

        //Texte Join
        if (channelID.equalsIgnoreCase("")) return;
        if (text.equalsIgnoreCase("")) return;
        TextChannel textChannel = event.getGuild().getTextChannelById(channelID);
        if (textChannel == null) return;

        textFinal = text.replaceAll("<guild>", event.getGuild().getName()).replaceAll("<member>", event.getUser().getAsMention()).replaceAll("<number>", String.valueOf(event.getGuild().getMembers().size()));

        if (selfMember.hasPermission(textChannel, Permission.MESSAGE_WRITE))
            textChannel.sendMessage(textFinal).queue();


        super.onGuildMemberJoin(event);
    }

    @Override
    public void onGuildMemberUpdatePending(@NotNull GuildMemberUpdatePendingEvent event) {
        autorole(event.getMember());

        super.onGuildMemberUpdatePending(event);
    }

    private void autorole(Member member) {
        Member selfMember = member.getGuild().getSelfMember();
        Role role = Main.getDatabase().getAutoRole(member.getGuild());
        if (role != null)
            if (!member.isPending())
                if (selfMember.canInteract(role) && selfMember.hasPermission(Permission.MANAGE_ROLES)) member.getGuild().addRoleToMember(member, role).reason("Autorole by AvenBot").queue();
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
