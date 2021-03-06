package fr.aven.bot.modules.tickets;

import fr.aven.bot.Main;
import fr.aven.bot.modules.database.TickDB;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
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

        event.getChannel().retrieveMessageById(event.getMessageId()).queue(msg -> {
            if (!msg.getAuthor().equals(event.getGuild().getSelfMember().getUser())) return;

            checkReact(event, channel, channelName);
        });

        super.onMessageReactionAdd(event);
    }

    private void createTicketChannel(TextChannel channel, Member member) {
        Category category = tickDB.getCategory(channel);
        if (category == null) return;
        if (Main.getTicketsDB().hasOpenTicket(member)) return;

        String name = tickDB.getName(channel);
        int ticketID = Main.getTicketsDB().createTicketID(member.getGuild());

        category.createTextChannel("ticket-" + ticketID)
                .addMemberPermissionOverride(member.getIdLong(), Arrays.asList(MESSAGE_READ, MESSAGE_WRITE), null)
                .queue(tc -> {
                    Main.getTicketsDB().addTicketChannel(tc, member.getId(), ticketID);
                    tc.sendMessage(new MessageBuilder()
                            .setContent(member.getAsMention())
                            .setEmbed(new EmbedBuilder()
                                    .setAuthor(name, "https://justaven.xyz")
                                    .setDescription(Main.getLanguage().getTextFor("ticket.closeText", channel.getGuild()))
                                    .setColor(member.getColor())
                                    .setFooter("AvenBot by Aven#1000")
                                    .build())
                            .build()).queue(msg -> msg.addReaction(close).queue());
                });

    }

    private void checkReact(MessageReactionAddEvent event, TextChannel channel, String channelName)
    {
        switch (event.getReactionEmote().getEmoji())
        {
            case ticket:
                if (!tickDB.isTicketRequestChannel(event.getTextChannel())) return;

                event.retrieveMessage().queue(msg -> createTicketChannel(event.getTextChannel(), event.getMember()));
                break;

            case close:
                //boolean ticketChannel = false;
                if (!Main.getTicketsDB().isTicketChannel(event.getTextChannel())) return;

                Member author = Main.getTicketsDB().getAuthorByTicketChannel(channel);
                if (author == null) return;
                System.out.println("ça c'est ok x2");
                if (!channel.getMembers().contains(author)) return;
                System.out.println("ça c'est ok x3");
                int ticketId = Main.getTicketsDB().getTicketIdByTicketChannel(channel);
                System.out.println("ça c'est ok x4");

                if (Main.getTicketsDB().isTicketClosed(channel)) return;
                System.out.println("ça c'est ok x5");

                /*for (Member member : channel.getMembers())
                {
                    if (member.getUser().isBot()) continue;

                    if (channelName.equalsIgnoreCase("ticket-"+member.getId())) {
                        ticketChannel = true;
                        ticketId = member.getId();
                        break;
                    }
                }*/

                /*if (ticketChannel)
                {*/

                Main.getTicketsDB().closeTicket(channel);
                    channel.getManager().setName("closed-"+ticketId).removePermissionOverride(author).queue();
                    channel.sendMessage(new EmbedBuilder()
                            .setAuthor(Main.getLanguage().getTextFor("ticket.closeTitle", channel.getGuild()), "https://www.justaven.xyz")
                            .setDescription(Main.getLanguage().getTextFor("ticket.closeDesc", channel.getGuild()))
                            .setColor(event.getMember().getColor())
                            .setFooter("AvenBot by Aven#1000")
                            .build())
                            .queue(msg -> {
                                msg.addReaction(reopen).queue();
                                msg.addReaction(delete).queue();

                                channel.getManager().setTopic(msg.getId()).queue();
                            });
                //}

                break;

            case reopen:
                if (!Main.getTicketsDB().isTicketClosed(event.getTextChannel())) return;
                if (!channelName.startsWith("closed")) return;

                Main.getTicketsDB().reopenTicket(channel);

                channel.getManager().setName(channelName.replace("closed", "ticket"))
                        .putPermissionOverride(Main.getTicketsDB().getAuthorByTicketChannel(channel),
                                Arrays.asList(MESSAGE_READ, MESSAGE_WRITE), null)
                        .queue();

                if (channel.getTopic() != null) channel.deleteMessageById(channel.getTopic()).queue(msg -> {}, error -> {});
                break;

            case delete:
                if (!Main.getTicketsDB().isTicketChannel(event.getTextChannel())) return;
                if (!Main.getTicketsDB().isTicketClosed(channel)) return;

                Main.getTicketsDB().ticketDeleted(channel);
                channel.sendMessage(new EmbedBuilder().setDescription(Main.getLanguage().getTextFor("tickets.closeConfirm", event.getGuild())).build()).queue();
                new Timer().schedule(new TicketsCloseTask(channel), 5000);
                break;
        }
    }

    private void deleteReaction(User user, MessageReaction reaction)
    {
        reaction.removeReaction(user).queue();
    }
}
