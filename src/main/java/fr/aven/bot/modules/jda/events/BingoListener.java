package fr.aven.bot.modules.jda.events;

import fr.aven.bot.Main;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class BingoListener extends ListenerAdapter
{
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (!Main.getBingoMap().channelHasBingo(event.getChannel())) return;

        try {
            int request = Integer.parseInt(event.getMessage().getContentRaw());
            if (!Main.getBingoMap().checkBingo(event.getChannel(), request)) return;

            event.getChannel().sendMessage("Good job "+event.getAuthor().getAsMention()+"! The winner number is: "+Main.getBingoMap().getValue(event.getChannel())).queue();
            Main.getBingoMap().deleteBingo(event.getChannel());
        } catch (NumberFormatException ignored) {}
    }
}
