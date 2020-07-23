package fr.aven.bot.events;

import fr.aven.bot.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

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

        textFinal = text.replaceAll("<guild>", event.getGuild().getName()).replaceAll("<member>", event.getUser().getAsTag());;

        event.getGuild().getTextChannelById(channelID).sendMessage(textFinal).queue();

        super.onGuildMemberJoin(event);
    }

    @Override
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event) {
        String text = Main.getDatabase().getTextJLB(event.getGuild().getId(), "textLeave");
        String channelID = String.valueOf(Main.getDatabase().queryId(event.getGuild(), "announceChannelID"));

        String textFinal = "";

        if (channelID.equalsIgnoreCase("")) return;
        if (text.equalsIgnoreCase("")) return;

        event.getGuild().retrieveBanList().queue(list -> {
            for (Guild.Ban bans : list)
            {
                if (bans.getUser() == event.getUser()) return;
            }
        });

        textFinal = text.replaceAll("<guild>", event.getGuild().getName()).replaceAll("<member>", event.getUser().getAsTag());;

        event.getGuild().getTextChannelById(channelID).sendMessage(textFinal).queue();

        super.onGuildMemberRemove(event);
    }

    @Override
    public void onGuildBan(@Nonnull GuildBanEvent event) {
        String text = Main.getDatabase().getTextJLB(event.getGuild().getId(), "textBan");
        String channelID = String.valueOf(Main.getDatabase().queryId(event.getGuild(), "announceChannelID"));

        String textFinal = "";

        if (channelID.equalsIgnoreCase("")) return;
        if (text.equalsIgnoreCase("")) return;

        textFinal = text.replaceAll("<guild>", event.getGuild().getName()).replaceAll("<member>", event.getUser().getAsTag());;

        event.getGuild().getTextChannelById(channelID).sendMessage(textFinal).queue();

        super.onGuildBan(event);
    }
}
