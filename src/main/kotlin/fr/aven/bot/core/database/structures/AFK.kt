package fr.aven.bot.core.database.structures

import fr.aven.bot.core.JDA
import fr.aven.bot.core.Main
import fr.aven.bot.core.database.structures.AFKs.guild
import fr.aven.bot.core.database.structures.AFKs.message
import fr.aven.bot.core.database.structures.AFKs.user
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.User
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object AFKs: Table("afk") {
    val user = varchar("user",25)
    val guild = varchar("guild", 25)
    val message = text("message")

    override val primaryKey = PrimaryKey(user, guild)
}

data class AFK(
    val user: User?,
    val guild: Guild?,
    val message: String
) {
    companion object {
        fun fromRow(row: ResultRow) = AFK(JDA.getUserById(row[user]), JDA.getGuildById(row[guild]), row[message])
    }
}