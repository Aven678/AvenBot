package fr.aven.bot.database;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.entity.Warn;
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQL
{
    private Connection connection = createConnection();
    private Statement statement;
    private Logger LOGGER = LoggerFactory.getLogger(SQL.class);

    public boolean resetConfig(Guild guild)
    {
        try {
            String removeGuild = "DELETE FROM guild WHERE guildID="+guild.getId();
            String muteRole = getMuteRole(guild);

            statement.executeUpdate(removeGuild);
            insertGuild(guild, muteRole);

        } catch (SQLException sqlException)
        {
            return false;
        }

        return true;
    }

    private Connection createConnection()
    {
        Connection connection1 = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection1 = DriverManager.getConnection(Main.getConfiguration().getString("sql.host", "jdbc:mysql://localhost/AvenBot?autoReconnect=true"),
                    //user
                    Main.getConfiguration().getString("sql.user", "root"),

                    //password
                    Main.getConfiguration().getString("sql.password", ""));

            statement = connection1.createStatement();

            Main.LOGGER.info("Connected to Database!");
        } catch (ClassNotFoundException e) {
            Main.LOGGER.error("Une erreur est survenue lors de la connexion à la base de données : ",e);
        } catch (SQLException e) {
            Main.LOGGER.error("Une erreur est survenue lors de la connexion à la base de données : ",e);
        }

        return connection1;
    }

    public Connection getConnection() {

        try {
            if (connection.isClosed()) connection = createConnection();
        } catch (SQLException sqlException) {
            LOGGER.error(sqlException.getMessage());
        }

        return connection;
    }

    public Statement getStatement()
    {
        return statement;
    }

    public void checkCmd(ICommand command) {
        String sql = "SELECT * FROM cmd_list WHERE cmdname=?";

        try
        {
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setString(1, command.getInvoke());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                insertCmd(command);
        } catch (SQLException e)
        {
            LOGGER.error(e.getMessage());
        }
    }

    public void insertCmd(ICommand command) {
        String SQL = "INSERT INTO cmd_list(cmdname, category, description, exemple)" + " VALUES(?,?,?,?)";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(SQL);
            preparedStatement.setString(1, command.getInvoke());
            preparedStatement.setString(2, command.getType().name());
            preparedStatement.setString(3, command.getHelp().getName());
            preparedStatement.setString(4, command.getHelp().getValue());

            preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            LOGGER.error(e.getMessage());
        }
    }

    public void checkGuild(Guild guild, String mutedRole) {
        String SQL = "SELECT * FROM guild WHERE guildID="+guild.getId();

        try
        {
            ResultSet resultSet = getConnection().createStatement().executeQuery(SQL);

            if (!resultSet.next())
                insertGuild(guild, mutedRole);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public boolean checkGuildIfExists(Guild guild)
    {
        String SQL = "SELECT * FROM guild WHERE guildID="+guild.getId();

        try {
            ResultSet resultSet = getConnection().createStatement().executeQuery(SQL);

            return resultSet.next();
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        }

        return false;
    }

    //INSERT IN DB
    public void insertGuild(Guild guild, String mutedRole) {
        //String SQL = "INSERT INTO guild(id, lang, adminRoles, modRoles, prefix, , idguild, joinmessage, leavemessage, announcechan)" + "VALUES(?,?,?,?,?,?)";
        String SQL = "INSERT INTO guild(guildID, lang, prefix, warnsLimit, warnsLimitType, muteRole, djRole, textJoin, textLeave, textBan, announceChannelID)" + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(SQL);
            preparedStatement.setString(1, guild.getId());
            preparedStatement.setString(2, "fr");
            preparedStatement.setString(3, Constants.PREFIX);
            preparedStatement.setInt(4, 3);
            preparedStatement.setString(5, "kick");
            preparedStatement.setString(6, mutedRole);
            preparedStatement.setString(7, null);
            preparedStatement.setString(8, null);
            preparedStatement.setString(9, null);
            preparedStatement.setString(10, null);
            preparedStatement.setString(11, null);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public String getTextFor(String request, Guild guild)
    {
        try {
            String checkLang = getLang(guild);
            String SQL = "SELECT text FROM language WHERE cmd=? AND language=?";

            PreparedStatement preparedStatement = getConnection().prepareStatement(SQL);
            preparedStatement.setString(1, request);
            preparedStatement.setString(2, checkLang);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getString(1);
        } catch (SQLException sqlException) {
            LOGGER.error(sqlException.getMessage());
        }

        return null;
    }

    public boolean isModerator (Member member, List modRoles, List adminRoles )
    {
        if (member.isOwner()) return true;
        if (member.hasPermission(net.dv8tion.jda.api.Permission.ADMINISTRATOR)) return true;
        if (Main.owner == (member.getUser().getIdLong())) { return true; }

        for(Role role : member.getRoles()) {
            if (adminRoles.contains(role.getId())) return true;
            if (modRoles.contains(role.getId())) return true;
        }

        return false;
    }

    public boolean isDJ (Member member)
    {
        if (member.isOwner()) return true;
        if (member.hasPermission(net.dv8tion.jda.api.Permission.ADMINISTRATOR)) return true;
        if (Main.owner == (member.getUser().getIdLong())) { return true; }

        Object object = queryId(member.getGuild(), "djRole");
        if(object == null) return false;
        else {
            String id = (String) object;
            for (Role role : member.getRoles()) { if(role.getId().equalsIgnoreCase(id)) return true; }
        }
        return false;
    }

    public boolean isAdmin (Member member, List adminRoles)
    {
        if (member.isOwner()) return true;
        if (member.hasPermission(net.dv8tion.jda.api.Permission.ADMINISTRATOR)) return true;
        if (Main.owner == (member.getUser().getIdLong())) { return true; }

        for(Role role : member.getRoles()) { if (adminRoles.contains(role.getId())) return true; }
        return false;
    }

    public boolean isBotOwner (Member member)
    {
        if (Main.owner == (member.getUser().getIdLong())) { return true; }
        return false;
    }

    public boolean checkPermission(Guild guild, User user, ICommand.Permission permission)
    {
        List adminRoles = rolesInDatabase("mod", guild);
        List modRoles = rolesInDatabase("admin", guild);
        Member member = guild.getMember(user);

        switch (permission) {
            case ADMIN: return isAdmin(member, adminRoles);
            case DJ: return isDJ(member);
            case MODO: return isModerator(member, modRoles, adminRoles);
            case USER: return true;
            case OWNER: return isBotOwner(member);
        }

        return false;
    }

    public List<String> rolesInDatabase(String typeOfRole, Guild guild)
    {
        switch (typeOfRole) {
            case "mod":
                return rolesID("modroles", guild);
            case "admin":
                return rolesID("adminroles", guild);
        }

        return new ArrayList<>();
    }

    public List<String> rolesID(String table, Guild guild)
    {
        String SQL = "SELECT * FROM "+table+" WHERE guildID=?";
        try
        {
            PreparedStatement preparedStatement = getConnection().prepareStatement(SQL);
            preparedStatement.setString(1, guild.getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> rolesId = new ArrayList<>();
            while (resultSet.next())
            {
                rolesId.add(resultSet.getString("roleID"));
            }

            return rolesId;
        } catch (SQLException e)
        {
            LOGGER.error(e.getMessage());
        }

        return new ArrayList<>();
    }

    public Object queryId(Guild guild, String coluum)
    {
        try
        {
            ResultSet resultSet = getConnection().createStatement().executeQuery("SELECT * FROM guild WHERE guildID="+guild.getId());

            if (resultSet.next())
                return resultSet.getObject(coluum);
        } catch (SQLException e)
        {
            LOGGER.error(e.getMessage());
        }

        return null;
    }

    public boolean setLang(Guild guild, String newLang)
    {
        try {
            getConnection().createStatement().executeUpdate("UPDATE guild SET lang="+newLang+" WHERE guildID="+guild.getId());
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }

        return true;
    }

    public String getLang(Guild guild)
    {
        String SQL = "SELECT lang FROM guild WHERE guildID="+guild.getId();
        ResultSet resultSet = null;
        try {
            resultSet = getConnection().createStatement().executeQuery(SQL);
            if (resultSet.next())
                return resultSet.getString(1);
            else
                return "en";
        } catch (SQLException e) {
            e.printStackTrace();
            return "en";
        }
    }

    public String getMuteRole(Guild guild) {
        String SQL = "SELECT muteRole FROM guild WHERE guildID = '" + guild.getId() + "'";
        try {
            ResultSet result = getConnection().createStatement().executeQuery(SQL);
            if (result.next()) {
                return result.getString(1);
            } else {
                return "=";
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return "=";
        }
    }

    //PREFIXES
    public String getGuildPrefix(Guild guild) {
        String SQL = "SELECT prefix FROM guild WHERE guildID = '" + guild.getId() + "'";
        try {
            ResultSet result = getConnection().createStatement().executeQuery(SQL);
            if (result.next()) {
                return result.getString(1);
            } else {
                return "=";
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return "=";
        }
    }

    public void setPrefix(Guild guild, String prefix) {
        String SQL = "UPDATE guild SET prefix = '" + prefix + "' WHERE guildID = '" + guild.getId() + "'";
        try {
            getConnection().createStatement().executeUpdate(SQL);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void addMod(String table, String idUser, String guildID, String dateTime, String reason)
    {
        String SQL = "INSERT INTO "+table+"(idUser, guildId, dateTime, reason) VALUES (?,?,?,?);";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(SQL);
            preparedStatement.setString(1,idUser);
            preparedStatement.setString(2, guildID);
            preparedStatement.setString(3, dateTime);
            preparedStatement.setString(4, reason);

            preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            LOGGER.error(e.getMessage());
        }
    }

    public void addKick(String idUser, String guildID, String dateTime, String reason)
    {
        addMod("kicks", idUser, guildID, dateTime, reason);
    }

    public void addBan(String idUser, String guildID, String dateTime, String reason)
    {
        addMod("bans", idUser, guildID, dateTime, reason);
    }

    public void addMute(String idUser, String guildID, String dateTime, String reason)
    {
        addMod("mutes", idUser, guildID, dateTime, reason);
    }

    public void addWarn(Warn warn)
    {
        addMod("warns", warn.getIdUser(), warn.getGuildID(), warn.getDateTime(), warn.getReason());
    }

    public List<Warn> listWarns(String idUser, String guildID)
    {
        List<Warn> warnList = new ArrayList<>();
        String SQL = "SELECT * FROM warns WHERE idUser=? AND guildID=?";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(SQL);
            preparedStatement.setString(1, idUser);
            preparedStatement.setString(2, idUser);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                warnList.add(new Warn(idUser, guildID, resultSet.getString("reason"), resultSet.getString("dateTime")));
            }

        } catch (SQLException e)
        {
            LOGGER.error(e.getMessage());
        }

        return warnList;
    }

    public void setDjRole(Role role)
    {
        String SQL = "UPDATE guild SET djRole = '" + role.getId() + "' WHERE guildID = '" + role.getGuild().getId() + "'";
        try {
            getConnection().createStatement().executeUpdate(SQL);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void setTextJLB(String guildID, String newMessage, String type)
    {
        try {
            String sql = "UPDATE guild SET ? = ? WHERE guildID=?";
            PreparedStatement preparedStatement = getConnection().prepareStatement(sql);

            preparedStatement.setString(1, type);
            preparedStatement.setString(2, newMessage);
            preparedStatement.setString(3, guildID);
        } catch (SQLException throwables) {
            LOGGER.error(throwables.getMessage());
        }
    }

    public void setMuteRole(Role mutedRole)
    {
        String SQL = "UPDATE guild SET muteRole = '" + mutedRole.getId() + "' WHERE guildID = '" + mutedRole.getGuild().getId() + "'";
        try {
            getConnection().createStatement().executeUpdate(SQL);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
