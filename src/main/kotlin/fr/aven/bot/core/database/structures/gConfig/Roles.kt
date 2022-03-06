package fr.aven.bot.core.database.structures.gConfig

import fr.aven.bot.core.database.structures.gConfig.Roles.admin
import fr.aven.bot.core.database.structures.gConfig.Roles.dj
import fr.aven.bot.core.database.structures.gConfig.Roles.id
import fr.aven.bot.core.database.structures.gConfig.Roles.mute
import fr.aven.bot.core.database.structures.gConfig.Roles.role
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Represent guild configuration on database
 *
 * @property id [String] guild id
 * @property role [String] role id
 * @property dj [Boolean] is dj role
 * @property mute [Boolean] is mute role
 * @property admin [Boolean] is admin role
 * @property mod [Boolean] is mod role
 */
object Roles : Table("roles") {
    val id = varchar("id", 25)
    val role = varchar("role", 25)
    val dj = bool("dj")
    val mute = bool("mute")
    val admin = bool("admin")
    val mod = bool("mod")
}

/**
 * data class to represent [Roles] table
 *
 * @property id [String] guild id
 * @property role [String] role id
 * @property dj [Boolean] is dj role
 * @property mute [Boolean] is mute role
 * @property admin [Boolean] is admin role
 * @property mod [Boolean] is mod role
 */
data class Role(
    val id: String,
    val role: String,
    val dj: Boolean,
    val mute: Boolean,
    val admin: Boolean,
    val mod: Boolean
) {
    companion object {
        /**
         * Create [Role] from [ResultRow]
         * @param row [ResultRow]
         */
        fun fromRaw(row: ResultRow) = Role(
            row[id],
            row[role],
            row[dj],
            row[mute],
            row[admin],
            row[Roles.mod]
        )

        /**
         * get list of [Role] from guild id
         * @param guildId [String] guild id
         */
        fun getGuildRoles(guildId: String): List<Role> {
            return transaction {
                Roles.select { Roles.id eq guildId }.map { fromRaw(it) }
            }
        }
    }
}