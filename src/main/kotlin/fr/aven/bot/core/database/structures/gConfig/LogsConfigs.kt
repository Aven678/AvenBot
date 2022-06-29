package fr.aven.bot.core.database.structures.gConfig

import fr.aven.bot.core.database.structures.gConfig.LogsConfigs.channel
import fr.aven.bot.core.database.structures.gConfig.LogsConfigs.id
import fr.aven.bot.core.database.structures.gConfig.LogsConfigs.logs
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.json.JSONObject

object LogsConfigs: Table("logs_config") {
    val id = integer("id").autoIncrement()
    val channel = varchar("channel", 25)
    val logs = text("logs")
}

data class LogsConfig(
    val id: Int,
    val channel: String,
    val logs: JSONObject
) {
    companion object {
        fun fromRow(row: ResultRow) = LogsConfig(row[id], row[channel], JSONObject(row[logs]))
    }
}