package fr.aven.bot.core.database.structures

import fr.aven.bot.core.database.structures.Roles.admin
import fr.aven.bot.core.database.structures.Roles.dj
import fr.aven.bot.core.database.structures.Roles.id
import fr.aven.bot.core.database.structures.Roles.mute
import fr.aven.bot.core.database.structures.Roles.role
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

/**
 * Represent guild configuration on database
 *
 * @property id [String] guild id
 * @property role [String] role id
 * @property dj [Boolean] is dj role
 * @property mute [Boolean] is mute role
 * @property admin [Boolean] is admin role
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
         */
        fun fromRaw(rle: ResultRow) = Role(
            rle[id],
            rle[role],
            rle[dj],
            rle[mute],
            rle[admin],
            rle[Roles.mod]
        )
    }
}