package fr.aven.bot.music;

import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class MusicReactionListener extends ListenerAdapter
{
    private List<String> emotes = Arrays.asList("⏮️","⏯️", "⏭️", "\uD83D\uDD01", "\uD83D\uDCDC", "❌");

    @Override
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
        if (event.getUser().isBot()) return;
        if (event.getGuild() == null) return;

        System.out.println(emotes.contains(event.getReactionEmote().getName()));
        event.getReaction().clearReactions().queue();
    }
}
