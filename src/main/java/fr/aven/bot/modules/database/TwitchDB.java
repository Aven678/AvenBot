package fr.aven.bot.modules.database;

import com.github.twitch4j.common.events.domain.EventChannel;
import fr.aven.bot.Main;
import fr.aven.bot.modules.notifications.twitch.entity.TwitchChannel;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TwitchDB
{
    private final Connection database;
    private final Logger LOGGER = LoggerFactory.getLogger(TwitchDB.class);

    public TwitchDB() {
        this.database = Main.getDatabase().getConnection();;
    }

    public List<TwitchChannel> getInfosOnlineEvent(EventChannel channel)
    {
        String SQL = "SELECT * FROM streams WHERE twitchChannelID="+channel.getId();
        return getTwitchChannels(SQL);
    }

    public List<TwitchChannel> getAllChannels()
    {
        String SQL = "SELECT * FROM streams";
        return getTwitchChannels(SQL);
    }

    @NotNull
    private List<TwitchChannel> getTwitchChannels(String SQL) {
        List<TwitchChannel> twitchChannels = new ArrayList<>();

        try {
            Statement statement = database.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next())
                twitchChannels.add(new TwitchChannel(resultSet.getString("discordChannelID"),
                        resultSet.getString("twitchChannelID"),
                        resultSet.getString("message"),
                        resultSet.getString("twitchChannelName")));

            resultSet.close();
            statement.close();
        } catch (SQLException sqlException) {
            LOGGER.error(sqlException.getMessage());
        }

        return twitchChannels;
    }
}
