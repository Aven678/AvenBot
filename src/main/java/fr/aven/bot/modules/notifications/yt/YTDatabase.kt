package fr.aven.bot.modules.notifications.yt

import fr.aven.bot.modules.jda.JDAManager
import net.dv8tion.jda.api.entities.TextChannel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object YouTubeNotifs : Table() {
    val id = integer("id").autoIncrement()
    val channelID = varchar("channelID", 255)
    val discordChannel = varchar("discordChannel", 25)
    val message = text("message").default("")

    override val primaryKey = PrimaryKey(id)
}

class YTDatabase(val url: String, val user: String, val pwd: String)
{
    val db: Database = Database.connect(url, "com.mysql.jdbc.Driver", user, pwd)

    fun getChannels(): MutableList<YTNotif> {
        val list: MutableList<YTNotif> = ArrayList()

        transaction {
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(YouTubeNotifs)

            for (ytNotif in YouTubeNotifs.selectAll())
            {
                list.add(YTNotif(ytNotif[YouTubeNotifs.channelID], ytNotif[YouTubeNotifs.discordChannel], ytNotif[YouTubeNotifs.message]))
            }
        }

        return list
    }
}
