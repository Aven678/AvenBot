package fr.aven.bot.entity;

public class Kick
{
    private final String idUser;
    private final String guildID;
    private final String moderatorID;
    private final String reason;
    private final String dateTime;

    public Kick(String idUser, String guildID, String moderatorID, String reason, String dateTime) {
        this.idUser = idUser;
        this.guildID = guildID;
        this.moderatorID = moderatorID;
        this.reason = reason;
        this.dateTime = dateTime;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getGuildID() {
        return guildID;
    }

    public String getModeratorID() { return moderatorID; }

    public String getReason() {
        return reason;
    }

    public String getDateTime() {
        return dateTime;
    }
}