package fr.aven.bot.core.database.structures

import com.sksamuel.hoplite.fp.invalid
import fr.aven.bot.core.database.structures.GuildConfigs.activities
import fr.aven.bot.core.database.structures.GuildConfigs.id
import fr.aven.bot.core.database.structures.GuildConfigs.lang
import fr.aven.bot.core.database.structures.GuildConfigs.warnConfig
import fr.aven.bot.core.database.structures.gConfig.*
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
    val logs = (integer("logs") references LogsConfigs.id)
    val activities = (integer("join_activity") references Activities.id)
    val warnConfig = (integer("warn_config") references WarnConfigs.id)

    override val primaryKey = PrimaryKey(id)
}

data class GuildConfig(
    val id: String,
    val lang: String,
    val logs: LogsConfig,
    val activities: Activity,
    val warnConfig: WarnConfig
) {
    companion object {
        private fun createGuildConfig(guild: Guild)
        {
            transaction {
                val logsID = LogsConfigs.insert {
                    it[channel] = "none"
                    it[logs] = "{}"
                } get LogsConfigs.id

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

                GuildConfigs.insert {
                    it[id] = guild.id
                    it[lang] = "en"
                    it[logs] = logsID
                    it[activities] = activityID
                    it[warnConfig] = warnID
                }
            }
        }

        fun getGuildConfig(guild: Guild): GuildConfig? {
            var config: GuildConfig? = getDatabaseConfig(guild)
            if (config == null)
            {
                createGuildConfig(guild)
                config = getDatabaseConfig(guild)
            }

            return config
        }

        private fun getDatabaseConfig(guild: Guild): GuildConfig? {
            var config: GuildConfig? = null
            transaction {
                val raw = (GuildConfigs innerJoin LogsConfigs innerJoin Activities innerJoin WarnConfigs).select { GuildConfigs.id.eq(guild.id) }.firstOrNull()
                config = raw?.let { fromRaw(it) }
            }

            return config
        }

        private fun fromRaw(raw: ResultRow): GuildConfig {
            return GuildConfig(
                raw[id],
                raw[lang],
                LogsConfig.fromRow(raw),
                Activity.fromRaw(raw),
                WarnConfig.fromRaw(raw)
            )
        }
    }
}
