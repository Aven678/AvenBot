package fr.aven.bot.modules.notifications.twitch.entity;

import fr.aven.bot.modules.jda.JDAManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

public class TwitchChannel
{
    private TextChannel discordChannel;
    private String channelID;
    private String channelName;
    private String message;

    public TwitchChannel(String discordChannelID, String channelID, String message, String channelName)
    {
        this.discordChannel = JDAManager.getShardManager().getTextChannelById(discordChannelID);
        this.channelID = channelID;
        this.message = message;
        this.channelName = channelName;
    }

    public TextChannel getDiscordChannel() {
        return discordChannel;
    }

    public String getChannelID() {
        return channelID;
    }

    public String getMessage() {
        return message;
    }

    public String getChannelName() { return channelName; }
}
