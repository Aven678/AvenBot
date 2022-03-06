package fr.aven.bot.core.database.structures

import fr.aven.bot.core.database.structures.Roles.admin
import fr.aven.bot.core.database.structures.Roles.dj
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
    val role = varchar("role", 25)
    val dj = bool("dj")
    val mute = bool("mute")
    val admin = bool("admin")
    val mod = bool("mod")

    override val primaryKey = PrimaryKey(role, name = "PK_Roles")
}

/**
 * data class to represent [Roles] table
 *
 * @property id [String] guild id
 * @property role [String] role id
 * @property dj [Boolean] is dj role
 * @property mute [Boolean] is mute role
 * @property admin [Boolean] is admin role
 */
data class Role(
    val role: String,
    val dj: Boolean,
    val mute: Boolean,
    val admin: Boolean
) {
    companion object {
        /**
         * Create [Role] from [ResultRow]
         */
        fun fromRaw(rle: ResultRow) = Role(
            rle[role],
            rle[dj],
            rle[mute],
            rle[admin]
        )
    }
}