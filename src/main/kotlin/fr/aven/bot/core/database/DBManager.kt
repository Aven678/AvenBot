package fr.aven.bot.core.database

import fr.aven.bot.core.database.structures.*
import fr.aven.bot.core.database.structures.gConfig.Activities
import fr.aven.bot.core.database.structures.gConfig.Roles
import fr.aven.bot.core.database.structures.gConfig.WarnConfigs
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Represents a database configuration.
 *
 * @property host [String] The host of the database.
 * @property port [String] The port of the database.
 * @property name [String] The name of the database.
 * @property user [String] The user of the database.
 * @property pass [String] The password of the database.
 */
data class DatabaseConfig(
    val host: String,
    val name: String,
    val port: String,
    val user: String,
    val pass: String
)

/**
 * Represents a database connection.
 *
 * @param config [DatabaseConfig] The configuration of the database.
 * @property instance [Database] The database connection.
 */
class DBManager(private val config: DatabaseConfig) {
    init {
        instance = Database.connect(
            "jdbc:mysql://${config.host}:${config.port}/${config.name}",
            driver = "com.mysql.cj.jdbc.Driver",
            user = config.user,
            password = config.pass)

        transaction {
            SchemaUtils.createMissingTablesAndColumns(
                Activities,
                WarnConfigs,
                Roles,
                GuildConfigs
            )
        }
    }

    companion object {
        /**
         * Represents the database connection instance.
         */
        lateinit var instance: Database
    }

}

