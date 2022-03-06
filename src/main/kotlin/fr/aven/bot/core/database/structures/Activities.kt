package fr.aven.bot.core.database.structures

import fr.aven.bot.core.database.structures.Activities.ban
import fr.aven.bot.core.database.structures.Activities.channel
import fr.aven.bot.core.database.structures.Activities.id
import fr.aven.bot.core.database.structures.Activities.join
import fr.aven.bot.core.database.structures.Activities.leave
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

/**
 * Represent guild configuration on database
 *
 * @property id [String] guild id
 * @property join [String] join message
 * @property leave [String] leave message
 * @property ban [String] ban message
 * @property channel [String] channel id
 */
object Activities : Table("activities") {
    val id = integer("id").autoIncrement()
    val join = text("join")
    val leave = text("leave")
    val ban = text("ban")

    val channel = varchar("channel", 25)
    override val primaryKey = PrimaryKey(id, name = "PK_Activities")
}


/**
 * data class to represent [Activities] table
 *
 * @property id [String] guild id
 * @property join [String] join message
 * @property leave [String] leave message
 * @property ban [String] ban message
 * @property channel [String] channel id
 */
data class Activity(
    val id: Int,
    val join: String,
    val leave: String,
    val ban: String,
    val channel: String
) {
    companion object {
        /**
         * Create a [WarnConfig] from a [ResultRow]
         */
        fun fromRaw(row: ResultRow) = Activity(
            id      = row[id],
            join    = row[join],
            leave   = row[leave],
            ban     = row[ban],
            channel = row[channel]
        )
    }
}