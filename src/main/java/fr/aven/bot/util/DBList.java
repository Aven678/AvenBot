package fr.aven.bot.util;

import fr.aven.bot.jda.JDAManager;
import org.discordbots.api.client.DiscordBotListAPI;

public class DBList
{
    private DiscordBotListAPI dblAPI = null;

    public void createDBL(String botId, String token)
    {
        this.dblAPI = new DiscordBotListAPI.Builder()
            .token(token)
            .botId(botId)
            .build();
    }

    public DiscordBotListAPI getDblAPI()
    {
        return dblAPI;
    }

    public void setServerNumber()
    {
        getDblAPI().setStats(JDAManager.getShardManager().getGuilds().size());
    }
}
