package fr.aven.bot.core

import com.sksamuel.hoplite.ConfigLoader
import dev.minn.jda.ktx.SLF4J
import dev.minn.jda.ktx.cache
import dev.minn.jda.ktx.default
import dev.minn.jda.ktx.intents
import fr.aven.bot.events.Listener
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.MemberCachePolicy

data class Config(
    val token: String,
    val guild_id: String,
    val logsChannelID: String,

    val firebaseUrl: String
)

class Main
{
    private val configuration = ConfigLoader().loadConfigOrThrow<Config>("config.yml")
    val jda: JDA
    val logger by SLF4J

    val firebase = Firebase()

    init {
        logger.info("Starting bot...")

        jda = default(configuration.token, enableCoroutines = true) {
            intents += listOf(GatewayIntent.GUILD_MEMBERS)
            setAutoReconnect(true)
            setActivity(Activity.watching("justaven.xyz"))
            addEventListeners(Listener)
            setMemberCachePolicy(MemberCachePolicy.ALL)
        }
    }

    companion object {

    }
}