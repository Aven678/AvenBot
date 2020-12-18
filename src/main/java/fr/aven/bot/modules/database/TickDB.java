package fr.aven.bot.modules.database;

import fr.aven.bot.Main;
import net.dv8tion.jda.api.entities.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TickDB
{
    private Connection connection;
    private Logger LOGGER = LoggerFactory.getLogger(TickDB.class);

    public boolean isTicketChannel(TextChannel channel)
    {
        boolean check = false;
        connection = Main.getDatabase().getConnection();

        try
        {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM tickets_channels WHERE channelId = '"+channel.getId()+"'";

            ResultSet resultSet = statement.executeQuery(SQL);
            if (resultSet.next()) check = true;

            resultSet.close();
            statement.close();
        }
        catch (SQLException throwables)
        {
            LOGGER.error(throwables.getMessage());
        }

        return check;
    }
}
