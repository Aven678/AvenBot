package fr.aven.bot.modules.tickets;

import fr.aven.bot.Main;
import fr.aven.bot.modules.database.TickDB;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;

import static net.dv8tion.jda.api.Permission.*;

public class TicketsEvent extends ListenerAdapter
{
    private final String ticket = "\uD83C\uDFAB";
    private final String close = "\uD83D\uDD10";
    private final String reopen = "\uD83D\uDD13";
    private final String delete = "❌";

    private final List<String> emotes = Arrays.asList(ticket, close, reopen, delete);


    private TickDB tickDB = new TickDB();

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event)
    {
        if (!event.getReactionEmote().isEmoji()) return;
        if (event.getUser().isBot()) return;

        if (!emotes.contains(event.getReactionEmote().getName())) return;

        TextChannel channel = event.getTextChannel();
        String channelName = channel.getName();

        switch (event.getReactionEmote().getEmoji())
        {
            case ticket:
                if (!tickDB.isTicketRequestChannel(event.getTextChannel())) return;

                event.retrieveMessage().queue(msg -> {
                    if (!msg.getMember().equals(event.getGuild().getSelfMember())) return;
                    createTicketChannel(event.getTextChannel(), event.getUser());
                });
                break;

            case close:
                boolean ticketChannel = false;
                String ticketId = "";

                for (Member member : channel.getMembers())
                {
                    if (member.getUser().isBot()) continue;

                    if (channelName.equalsIgnoreCase("ticket-"+member.getId())) {
                        ticketChannel = true;
                        ticketId = member.getId();
                        break;
                    }
                }

                if (ticketChannel)
                {
                    channel.getManager().setName("closed-"+ticketId).removePermissionOverride(event.getGuild().getMemberById(ticketId)).queue();
                    String finalTicketId = ticketId;
                    channel.sendMessage(new EmbedBuilder()
                            .setAuthor(Main.getDatabase().getTextFor("ticket.closeTitle", channel.getGuild()), "https://www.justaven.xyz")
                            .setDescription(Main.getDatabase().getTextFor("ticket.closeDesc", channel.getGuild()))
                            .setColor(event.getMember().getColor())
                            .setFooter("AvenBot by Aven#1000")
                            .build())
                        .queue(msg -> {
                            msg.addReaction(reopen).queue();
                            msg.addReaction(delete).queue();

                            channel.getManager().setTopic(finalTicketId).queue();
                        });
                }

                break;

            case reopen:
                if (!channelName.startsWith("closed")) return;

                var memberId = channelName;
                memberId = StringUtils.substringBefore("-", "");

                channel.getManager().setName(channelName.replace("closed", "ticket"))
                        .putPermissionOverride(event.getGuild().getMemberById(memberId), Arrays.asList(MESSAGE_READ, MESSAGE_WRITE), null)
                        .queue();

                channel.deleteMessageById(channel.getTopic()).queue(msg -> {}, error -> {});
                break;

            case delete:
                channel.sendMessage(new EmbedBuilder().setDescription(Main.getDatabase().getTextFor("tickets.closeConfirm", event.getGuild())).build()).queue();
                new Timer().schedule(new TicketsCloseTask(channel), 5000);
                break;
        }

        event.getReaction().removeReaction(event.getUser()).queue();

        super.onMessageReactionAdd(event);
    }

    private void createTicketChannel(TextChannel channel, User user)
    {
        Category category = tickDB.getCategory(channel);
        if (category == null) return;

        if (category.getGuild().getTextChannelsByName("ticket-"+user.getId(), true).size() == 0)
        {
            String name = tickDB.getName(channel);

            category.createTextChannel("ticket-"+user.getId())
                    .addMemberPermissionOverride(user.getIdLong(), Arrays.asList(MESSAGE_READ, MESSAGE_WRITE), null)
                    .queue(tc -> {
                        tc.sendMessage(new MessageBuilder()
                                .setContent(user.getAsMention())
                                .setEmbed(new EmbedBuilder()
                                        .setAuthor(name, "https://justaven.xyz")
                                        .setDescription(Main.getDatabase().getTextFor("ticket.closeText", channel.getGuild()))
                                        .setColor(channel.getGuild().getMember(user).getColor())
                                        .setFooter("AvenBot by Aven#1000")
                                        .build())
                                .build()).queue(msg -> msg.addReaction(close).queue());
                    });
        }
    }
}
