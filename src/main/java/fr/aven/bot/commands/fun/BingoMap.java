package fr.aven.bot.commands.fun;

import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.Map;

public class BingoMap
{
    private Map<Long, Integer> bingoMap = new HashMap();

    public void addBingoChannel(TextChannel channel, int bingo)
    {
        if (!channelHasBingo(channel)) bingoMap.put(channel.getIdLong(), bingo);
    }

    public boolean checkBingo(TextChannel channel, int request)
    {
        if (channelHasBingo(channel) && bingoMap.get(channel.getIdLong()).equals(request)) return true;
        return false;
    }

    public boolean channelHasBingo(TextChannel channel)
    {
        return bingoMap.containsKey(channel.getIdLong());
    }

    public void deleteBingo(TextChannel channel)
    {
        if (channelHasBingo(channel)) bingoMap.remove(channel.getIdLong());
    }

    public int getValue(TextChannel channel)
    {
        if (channelHasBingo(channel)) return bingoMap.get(channel.getIdLong());
        return 0;
    }
}
