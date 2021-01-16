package fr.aven.bot.modules.jda.events;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class AFKListener extends ListenerAdapter
{
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (event.getMessage().isWebhookMessage()) return;

        if (event.getMessage().getContentRaw().startsWith(Main.getDatabase().getGuildPrefix(event.getGuild()))) return;

        if (Main.getDatabase().isAFK(event.getGuild(), event.getAuthor()))
        {
            Main.getDatabase().removeAFK(event.getGuild(), event.getAuthor());
            event.getChannel().sendMessage(event.getAuthor().getAsMention()+" is no longer AFK.").queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
            return;
        }

        if (event.getMessage().getMentionedUsers().size() == 0) return;

        for (User user : event.getMessage().getMentionedUsers()) {
            if (Main.getDatabase().isAFK(event.getGuild(), user))
            {
                String reason = Main.getDatabase().getReasonAFK(event.getGuild(), user);
                event.getChannel().sendMessage(user.getAsTag()+" is AFK"+ (reason.equalsIgnoreCase("") ? "" : ", reason: " + reason)).queue(msg -> msg.delete().queueAfter(5, TimeUnit.SECONDS));
            }
        }

    }
}
