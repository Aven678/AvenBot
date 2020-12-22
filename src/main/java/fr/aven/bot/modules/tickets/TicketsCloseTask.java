package fr.aven.bot.modules.tickets;

import net.dv8tion.jda.api.entities.TextChannel;

import java.util.TimerTask;

public class TicketsCloseTask extends TimerTask
{
    private final TextChannel channel;

    public TicketsCloseTask(TextChannel channel)
    {
        this.channel = channel;
    }

    @Override
    public void run() {
        channel.delete().queue();
    }
}
