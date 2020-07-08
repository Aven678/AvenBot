package fr.aven.bot.util;

import net.dv8tion.jda.api.entities.Message;

import java.util.TimerTask;

public class MessageTask extends TimerTask
{
    private final Message msg;

    public MessageTask(Message msg)
    {
        this.msg = msg;
    }

    @Override
    public void run() {
        msg.delete().queue();
    }
}
