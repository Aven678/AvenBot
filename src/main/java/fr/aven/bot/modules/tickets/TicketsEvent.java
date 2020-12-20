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
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static net.dv8tion.jda.api.Permission.*;

public class TicketsEvent extends ListenerAdapter
{
    private final String ticket = "\uD83C\uDFAB";
    private final String close = "\uD83D\uDD10";
    private TickDB tickDB = new TickDB();

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event)
    {
        if (!event.getReactionEmote().isEmoji()) return;

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
                TextChannel channel = event.getTextChannel();
                String channelName = channel.getName();

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
                    channel.getManager().setName("closed-"+ticketId).clearOverridesAdded().queue();
                }

                break;
        }

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
                                        .setDescription(name + "\n" + Main.getDatabase().getTextFor("ticket.closeText", channel.getGuild()))
                                        .setColor(channel.getGuild().getMember(user).getColor())
                                        .build())
                                .build()).queue(msg -> msg.addReaction(close).queue());
                    });
        }
    }
}
