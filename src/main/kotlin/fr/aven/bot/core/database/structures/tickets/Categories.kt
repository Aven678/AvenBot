package fr.aven.bot.core.database.structures.tickets

import fr.aven.bot.core.JDA
import fr.aven.bot.core.database.structures.tickets.Categories.channel
import fr.aven.bot.core.database.structures.tickets.Categories.guild
import fr.aven.bot.core.database.structures.tickets.Categories.id
import fr.aven.bot.core.database.structures.tickets.Categories.message
import fr.aven.bot.core.database.structures.tickets.Categories.name
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.json.JSONObject

object Categories: Table("tic_categories") {
    val id = integer("id").autoIncrement()
    val guild = varchar("guild", 25)
    val name = text("name")
    val channel = varchar("channel", 25)
    val message = text("message")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}

data class Category(
    val id: Int,
    val guild: Guild?,
    val name: String,
    val channel: GuildChannel?,
    val message: JSONObject
) {
    companion object {
        fun fromRow(row: ResultRow) = Category(row[id],
            JDA.getGuildById(row[guild]),
            row[name],
            JDA.getGuildChannelById(row[channel]),
            JSONObject(row[message])
        )
    }
}