package fr.aven.bot.modules.database

import fr.aven.bot.modules.jda.JDAManager
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Role
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.text
import org.ktorm.schema.varchar

object Guilds : Table<Nothing>("guild") {
    val id = int("id").primaryKey()
    val guildID = varchar("guildID")
    val lang = varchar("lang")
    val prefix = varchar("prefix")
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

data class GuildData(val guildID: String?, val lang: String?, val prefix: String?, val warnsLimit: Int?, val warnsLimitType: String?, val muteRole: String?, val djRole: String?, val textJoin: String?, val textLeave: String?, val textBan: String?, val announceChannelID: String?, val autoRoleID: String?)

class Database(val url: String, val db: String, val user: String, val pwd: String)
{
    lateinit var database: Database

    fun connect(): fr.aven.bot.modules.database.Database {
        database = Database.connect(
            url = "jdbc:mysql://$url/$db?useSSL=false",
            driver = "com.mysql.jdbc.Driver",
            user = user,
            password = pwd
        )

        return this
    }

    fun insertGuild(discordGuild: Guild, mutedRole: String) {
        database.insert(Guilds) {
            set(it.guildID, discordGuild.id)
            set(it.lang, "en")
            set(it.prefix, "=")
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

    fun getAutoRole(discordGuild: Guild): Role {
        var roleID: String? = null

        database.from(Guilds)
            .select(Guilds.autoRoleID)
            .where(Guilds.guildID eq discordGuild.id)
            .limit(0,1)
            .map { row -> roleID = row[Guilds.autoRoleID] }

        return roleID?.let { JDAManager.getShardManager().getRoleById(it) }!!;
    }
}
