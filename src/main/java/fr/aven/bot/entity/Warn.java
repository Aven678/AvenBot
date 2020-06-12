package fr.aven.bot.entity;

public class Warn
{
    private final String idUser;
    private final String guildID;
    private final String reason;
    private final String dateTime;

    public Warn(String idUser, String guildID, String reason, String dateTime) {
        this.idUser = idUser;
        this.guildID = guildID;
        this.reason = reason;
        this.dateTime = dateTime;
    }

    public String getIdUser() {
        return idUser;
    }

    public String getGuildID() {
        return guildID;
    }

    public String getReason() {
        return reason;
    }

    public String getDateTime() {
        return dateTime;
    }
}
