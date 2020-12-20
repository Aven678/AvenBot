package fr.aven.bot.modules.database;

import fr.aven.bot.Main;
import fr.aven.bot.modules.jda.JDAManager;
import net.dv8tion.jda.api.entities.Category;
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

    public boolean isTicketRequestChannel(TextChannel channel)
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

    public Category getCategory(TextChannel channel)
    {
        Category category = null;
        connection = Main.getDatabase().getConnection();

        try
        {
            Statement statement = connection.createStatement();
            String SQL = "SELECT category FROM tickets_channels WHERE channelId = '"+channel.getId()+"'";

            ResultSet resultSet = statement.executeQuery(SQL);
            if (resultSet.next())
                category = JDAManager.getShardManager().getCategoryById(resultSet.getString(1));

            resultSet.close();
            statement.close();
        } catch (Exception e)
        {
            LOGGER.error(e.getMessage());
        }

        return category;
    }

    public String getName(TextChannel channel)
    {
        String name = null;
        connection = Main.getDatabase().getConnection();

        try
        {
            Statement statement = connection.createStatement();
            String SQL = "SELECT desc_name FROM tickets_channels WHERE channelId = '"+channel.getId()+"'";

            ResultSet resultSet = statement.executeQuery(SQL);
            if (resultSet.next())
                name = resultSet.getString(1);

            resultSet.close();
            statement.close();
        } catch (Exception e)
        {
            LOGGER.error(e.getMessage());
        }

        return name;
    }
}
