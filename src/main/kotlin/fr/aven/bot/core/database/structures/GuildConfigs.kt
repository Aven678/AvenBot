package fr.aven.bot.core.database.structures

import com.sksamuel.hoplite.fp.invalid
import fr.aven.bot.core.database.structures.GuildConfigs.activities
import fr.aven.bot.core.database.structures.GuildConfigs.id
import fr.aven.bot.core.database.structures.GuildConfigs.lang
import fr.aven.bot.core.database.structures.GuildConfigs.warnConfig
import net.dv8tion.jda.api.entities.Guild
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

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
    val activities = (integer("join_activity") references Activities.id)
    val warnConfig = (integer("warn_config") references WarnConfigs.id)

    override val primaryKey = PrimaryKey(id)
}

data class GuildConfig(
    val id: String,
    val lang: String,
    val activities: Activity,
    val warnConfig: WarnConfig
) {
    companion object {
        fun createGuildConfig(guild: Guild): GuildConfig?
        {
            var config: GuildConfig? = null

            transaction {
                val activityID = Activities.insert {
                    it[join] = "nothing"
                    it[leave] = "nothing"
                    it[ban] = "nothing"
                    it[channel] = "nothing"
                } get Activities.id

                val warnID = WarnConfigs.insert {
                    it[type] = "kick"
                    it[limit] = 5
                } get WarnConfigs.id

                val insert = GuildConfigs.insert {
                    it[id] = guild.id
                    it[lang] = "en"
                    it[activities] = activityID
                    it[warnConfig] = warnID
                }.resultedValues?.get(0)

                config = insert?.let { fromRaw(it) }
            }

            return config
        }

        fun getGuildConfig(guild: Guild): GuildConfig? {
            var config: GuildConfig? = null

            transaction {
                val raw = (GuildConfigs innerJoin Activities innerJoin WarnConfigs).select { GuildConfigs.id.eq(guild.id) }.firstOrNull()
                config = raw?.let { fromRaw(it) }
            }

            return config?: createGuildConfig(guild)
        }

        private fun fromRaw(raw: ResultRow): GuildConfig {
            return GuildConfig(
                raw[id],
                raw[lang],
                Activity.fromRaw(raw),
                WarnConfig.fromRaw(raw)
            )
        }
    }
}
