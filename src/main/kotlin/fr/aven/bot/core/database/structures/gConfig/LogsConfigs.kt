package fr.aven.bot.core.database.structures.gConfig

import fr.aven.bot.core.JDA
import fr.aven.bot.core.database.structures.gConfig.LogsConfigs.channel
import fr.aven.bot.core.database.structures.gConfig.LogsConfigs.id
import fr.aven.bot.core.database.structures.gConfig.LogsConfigs.logs
import net.dv8tion.jda.api.entities.GuildChannel
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.json.JSONObject

object LogsConfigs: Table("logs_config") {
    val id = integer("id").autoIncrement()
    val channel = varchar("channel", 25)
    val logs = text("logs").default("{}")

    override val primaryKey = PrimaryKey(id, name = "PK_LogsConfigs")
}

data class LogsConfig(
    val id: Int,
    val channel: GuildChannel?,
    val logs: JSONObject
) {
    companion object {
        fun fromRow(row: ResultRow) = LogsConfig(row[id], if (row[channel] == "none") null else JDA.getGuildChannelById(row[channel]), JSONObject(row[logs]))
    }
}