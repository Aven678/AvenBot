package fr.aven.bot.modules.database

import fr.aven.bot.Constants
import fr.aven.bot.modules.jda.JDAManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Role
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.schema.*

object Guilds : Table<Nothing>("guild") {
    val id = int("id").primaryKey()
    val guildID = varchar("guildID")
    val lang = enum<Lang>("lang")
    val prefix = text("prefix")
    val warnsLimit = int("warnsLimit")
    val warnsLimitType = varchar("warnsLimitType")
    val muteRole = varchar("muteRole")
    val djRole = varchar("djRole")
    val textJoin = text("textJoin")
    val textLeave = text("textLeave")
    val textBan = text("textBan")
    val announceChannelID = varchar("announceChannelID")
    val autoRoleID = varchar("autoRoleID")
}

enum class Lang(s: String) {
    EN("en"),
    FR("fr")
}

class DatabaseManager(url: String, user: String, pwd: String)
{
    var db: Database

    init {
        db = Database.connect(url, "com.mysql.jdbc.Driver", user, pwd)
    }

    fun clearConfig(guild: Guild)
    {
        db.delete(Guilds) {it.guildID eq guild.id}
    }

    fun checkGuild(guild: Guild, mutedRole: String)
    {
        if (db.from(Guilds)
            .select(Guilds.guildID)
            .where { Guilds.guildID eq guild.id }
            .totalRecords > 0) return

        db.insert(Guilds) {
            set(it.guildID, guild.id)
            set(it.lang, Lang.EN)
            set(it.prefix, Constants.PREFIX)
            set(it.warnsLimit, 3)
            set(it.warnsLimitType, "kick")
            set(it.muteRole, mutedRole)
            set(it.djRole, null)
            set(it.textJoin, null)
            set(it.textLeave, null)
            set(it.textBan, null)
            set(it.announceChannelID, null)
        }
    }

    fun autoRole(guild: Guild): Role?
    {
        db.from(Guilds)
            .select(Guilds.guildID)
            .where { Guilds.guildID eq guild.id }
            .forEach {
                if (it[Guilds.autoRoleID] != null) return JDAManager.manager.getRoleById(it[Guilds.autoRoleID]!!)
            }

        return null
    }
}