package fr.aven.bot.modules.tickets;

import fr.aven.bot.modules.database.TickDB;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class TicketsEvent extends ListenerAdapter
{
    private String ticket = "\uD83C\uDFAB";
    private TickDB tickDB = new TickDB();

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event)
    {
        if (!event.getReactionEmote().isEmoji()) return;
        if (!event.getReactionEmote().getEmoji().equalsIgnoreCase(ticket)) return;
        if (!tickDB.isTicketChannel(event.getTextChannel())) return;

        System.out.println("test cool");

        super.onMessageReactionAdd(event);
    }
}
