package fr.aven.bot.modules.tickets;

import fr.aven.bot.Main;
import fr.aven.bot.modules.database.SQL;
import fr.aven.bot.modules.jda.JDAManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TicketsChannelDB
{
    private SQL database = Main.getDatabase();
    private Logger LOGGER = LoggerFactory.getLogger(TicketsChannelDB.class);

    public boolean addTicketChannel(TextChannel channel, String author, int ticketID)
    {
        String SQL = "INSERT INTO tickets_id(guildId,channelId,author, guildTicketID) VALUES (?,?,?,?)";

        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(SQL);

            preparedStatement.setString(1, channel.getGuild().getId());
            preparedStatement.setString(2, channel.getId());
            preparedStatement.setString(3, author);
            preparedStatement.setInt(4, ticketID);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            return true;
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        }

        return false;
    }

    public int createTicketID(Guild guild)
    {
        String SQL = "SELECT * FROM tickets_id WHERE guildId="+guild.getId();

        try {
            Statement statement = database.getConnection().createStatement();

            ResultSet resultSet = statement.executeQuery(SQL);
            int ticketId = 1;

            while (resultSet.next())
                ticketId = ticketId + 1;

            resultSet.close();
            statement.close();
            return ticketId;
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        }

        return 0;
    }

    public boolean isTicketClosed(TextChannel channel)
    {
        String SQL = "SELECT * FROM tickets_id WHERE channelId="+channel.getId();
        var result = false;

        try {
            Statement statement = database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            if (resultSet.next())
                if (resultSet.getInt("closed") == 1) result = true;

            resultSet.close();
            statement.close();
        } catch (SQLException sqlException)
        {
            LOGGER.error(sqlException.getMessage());
        }

        return result;
    }

    public boolean hasOpenTicket(Member member)
    {
        String SQL = "SELECT * FROM tickets_id WHERE guildId="+member.getGuild().getId()+" AND author="+member.getId();
        var result = false;

        try {
            Statement statement = database.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(SQL);
            if (resultSet.next())
                result = resultSet.getInt("closed") == 0;

            resultSet.close();
            statement.close();
        } catch (SQLException sqlException)
        {
            LOGGER.error(sqlException.getMessage());
        }

        return result;
    }

    public void ticketDeleted(TextChannel channel)
    {
        String SQL = "UPDATE tickets_id SET deleted=1 WHERE channelId="+channel.getId();

        try {
            Statement statement = database.getConnection().createStatement();
            statement.executeUpdate(SQL);

            statement.close();
        } catch (SQLException sqlException)
        {
            LOGGER.error(sqlException.getMessage());
        }
    }

    public TextChannel getTicketChannelByAuthor(Member member)
    {
        String SQL = "SELECT * FROM tickets_id WHERE guildId=? AND author=?";
        TextChannel channel = null;

        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(SQL);

            preparedStatement.setString(1, member.getGuild().getId());
            preparedStatement.setString(2, member.getUser().getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                channel = JDAManager.getShardManager().getTextChannelById(resultSet.getString("channelId"));

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException sqlException) {
            LOGGER.error(sqlException.getMessage());
        }

        return channel;
    }

    public Member getAuthorByTicketChannel(TextChannel channel)
    {
        String SQL = "SELECT * FROM tickets_id WHERE guildId=? AND channelId=?";
        Member member = null;

        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(SQL);

            preparedStatement.setString(1, channel.getGuild().getId());
            preparedStatement.setString(2, channel.getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                member = channel.getGuild().getMember(JDAManager.getShardManager().getUserById(resultSet.getString("author")));

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException sqlException) {
            LOGGER.error(sqlException.getMessage());
        }

        return member;
    }

    public int getTicketIdByTicketChannel(TextChannel channel)
    {
        String SQL = "SELECT * FROM tickets_id WHERE guildId=? AND channelId=?";
        var ticketId = 0;

        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(SQL);

            preparedStatement.setString(1, channel.getGuild().getId());
            preparedStatement.setString(2, channel.getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                ticketId = resultSet.getInt("guildTicketID");

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException sqlException) {
            LOGGER.error(sqlException.getMessage());
        }

        return ticketId;
    }

    public void closeTicket(TextChannel channel)
    {
        changeCloseStatus(true, channel);
    }

    public void reopenTicket(TextChannel channel)
    {
        changeCloseStatus(false, channel);
    }

    private void changeCloseStatus(boolean toClose, TextChannel channel)
    {
        var request = toClose == true ? 1 : 0;
        String SQL = "UPDATE tickets_id SET closed="+ request +" WHERE channelId="+channel.getId();

        try {
            Statement statement = database.getConnection().createStatement();
            statement.executeUpdate(SQL);

            statement.close();
        } catch (SQLException sqlException)
        {
            LOGGER.error(sqlException.getMessage());
        }
    }

    public boolean isTicketChannel(TextChannel channel)
    {
        String SQL = "SELECT * FROM tickets_id WHERE guildId=? AND channelId=?";


        try {
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(SQL);

            preparedStatement.setString(1, channel.getGuild().getId());
            preparedStatement.setString(2, channel.getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return true;
        } catch (SQLException sqlException) {
            LOGGER.error(sqlException.getMessage());
        }

        return false;
    }
}
