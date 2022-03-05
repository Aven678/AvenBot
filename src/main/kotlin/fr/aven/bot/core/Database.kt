package fr.aven.bot.core

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

object GuildConfig: Table() {
    val id = varchar("id",25)
    val lang = varchar("lang", 5)
    val activities = (integer("join_activity") references Activities.id).foreignKey
    val warnConfig = (integer("warn_config") references WarnConfig.id).foreignKey
    val roles = (integer("roles") references Roles.id).foreignKey

    override val primaryKey = PrimaryKey(id, name = "PK_Guild")
}

object Activities: Table() {
    val id = integer("id").autoIncrement()
    val join = text("join")
    val leave = text("leave")
    val ban = text("ban")

    val channel = varchar("channel", 25)
    override val primaryKey = PrimaryKey(id, name = "PK_Activities")
}

object WarnConfig: Table() {
    val id = integer("id").autoIncrement()
    val type = varchar("type", 10)
    val limit = integer("limit")

    override val primaryKey = PrimaryKey(id, name = "PK_WarnConfig")
}

object Roles: Table() {
    val id = integer("id").autoIncrement()
    val role = varchar("role", 25)
    val dj = bool("dj")
    val mute = bool("mute")
    val admin = bool("admin")

    override val primaryKey = PrimaryKey(id, name = "PK_Roles")
}

data class DatabaseConfig(val host: String, val name: String, val port: String, val user: String, val pass: String)

class Database(private val config: DatabaseConfig)
{
    lateinit var db: Database

    init {
        db = Database.connect("jdbc:mysql://${config.host}:${config.port}/${config.name}", driver = "com.mysql.cj.jdbc.Driver", user = config.user, password = config.pass)

        transaction {
            SchemaUtils.createMissingTablesAndColumns (GuildConfig, Activities, WarnConfig, Roles)
        }
    }

}