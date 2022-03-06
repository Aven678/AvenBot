package fr.aven.bot.core.database.structures

import fr.aven.bot.core.database.structures.GuildConfigs.activities
import fr.aven.bot.core.database.structures.GuildConfigs.id
import fr.aven.bot.core.database.structures.GuildConfigs.lang
import fr.aven.bot.core.database.structures.GuildConfigs.roles
import fr.aven.bot.core.database.structures.GuildConfigs.warnConfig
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

/**
 * Represent guild configuration on database
 *
 * @property id [String] id of guild
 * @property lang [String] language of guild
 * @property activities [Activities] activities of guild
 * @property warnConfig [WarnConfigs] warn config of guild
 * @property roles [Roles] roles of guild
 */
object GuildConfigs : Table("guild_configs") {
    val id = varchar("id", 25)
    val lang = varchar("lang", 5)
    val activities = (integer("join_activity") references Activities.id).foreignKey
    val warnConfig = (integer("warn_config") references WarnConfigs.id).foreignKey
    val roles = (integer("roles") references Roles.id).foreignKey

    override val primaryKey = PrimaryKey(id)
}

data class GuildConfig(
    val id: String,
    val lang: String,
    val activities: Activity,
    val warnConfig: WarnConfig,
    val roles: Role
) {
    companion object {
        fun fromRaw(raw: ResultRow): GuildConfig {
            return GuildConfig(
                raw[id],
                raw[lang],
                Activity.fromRaw(raw[activities]),
                WarnConfig.fromRaw(raw[warnConfig]),
                Role.fromRaw(raw[roles])
            )
        }
    }
}
