package fr.aven.bot.core.database.structures

import fr.aven.bot.core.database.structures.WarnConfigs.id
import fr.aven.bot.core.database.structures.WarnConfigs.limit
import fr.aven.bot.core.database.structures.WarnConfigs.type
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

/**
 * Represent guild configuration on database
 *
 * @property id [Integer] guild id
 * @property type [String] type of warn
 * @property limit [String] limit of warn
 */
object WarnConfigs : Table("warn_configs") {
    val id = integer("id").autoIncrement()
    val type = varchar("type", 10)
    val limit = integer("limit")

    override val primaryKey = PrimaryKey(id, name = "PK_WarnConfig")
}

/**
 * data class to represent [WarnConfigs]
 *
 * @property id [String] guild id
 * @property type [String] type of warn
 * @property limit [String] limit of warn
 */
data class WarnConfig(
    val id: Int,
    val type: String,
    val limit: Int
) {
    companion object {
        /**
         * Create a [WarnConfig] from a [ResultRow]
         */
        fun fromRaw(row: ResultRow) = WarnConfig(
            id = row[id],
            type = row[type],
            limit = row[limit]
        )
    }
}