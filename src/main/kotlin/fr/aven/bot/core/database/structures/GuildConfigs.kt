package fr.aven.bot.core.database.structures

import com.sksamuel.hoplite.fp.invalid
import fr.aven.bot.core.database.structures.GuildConfigs.activities
import fr.aven.bot.core.database.structures.GuildConfigs.id
import fr.aven.bot.core.database.structures.GuildConfigs.lang
import fr.aven.bot.core.database.structures.GuildConfigs.warnConfig
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
    val roles = (varchar("role", 25) references Roles.id)

    override val primaryKey = PrimaryKey(id)
}

data class GuildConfig(
    val id: String,
    val lang: String,
    val activities: Activity,
    val warnConfig: WarnConfig
) {
    companion object {
        fun insert() {
            transaction {
                val activityID = Activities.insert {
                    it[ban] = "sussy"
                    it[join] = "sussy"
                    it[leave] = "sussy"
                    it[channel] = "sussy"
                } get Activities.id

                val role1 = Roles.insert {
                    it[role] = "sussy"
                    it[dj] = false
                    it[admin] = false
                    it[mod] = false
                } get Roles.id

                val role2 = Roles.insert {
                    it[role] = "sussy2"
                    it[dj] = false
                    it[admin] = false
                    it[mod] = false
                } get Roles.id

                val warnID = WarnConfigs.insert {
                    it[limit] = 2
                    it[type] = "sussy"
                } get WarnConfigs.id

                GuildConfigs.insert {
                    it[id] = "sussy"
                    it[lang] = "en"
                    it[activities] = activityID
                    it[warnConfig] = warnID
                    it[roles] = "sussy"
                }
            }
        }

        fun test() {
            transaction {
                addLogger(StdOutSqlLogger)
                val test = (GuildConfigs innerJoin Activities innerJoin WarnConfigs innerJoin Roles).select { GuildConfigs.id.eq("sussy") }.first()

                println(test)
            }
        }

        fun fromRaw(raw: ResultRow): GuildConfig {
            return GuildConfig(
                raw[id],
                raw[lang],
                Activity.fromRaw(raw),
                WarnConfig.fromRaw(raw)
            )
        }
    }
}
