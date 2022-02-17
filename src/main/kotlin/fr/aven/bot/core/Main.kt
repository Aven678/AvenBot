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
import java.io.File

data class Config(
    val token: String,
    val guild_id: String,
    val logsChannelID: String,

    val firebaseUrl: String,

    val ipv6_block: String = "none"
)

class Main
{
    lateinit var configuration: Config
    lateinit var jda: JDA
    val logger by SLF4J

    val firebase = Firebase(configuration)

    init {
        logger.info("Starting bot...")
        if (!checkConfigFile()) start()
    }

    private fun start() {
        configuration = ConfigLoader().loadConfigOrThrow<Config>("config.yml")

        jda = default(configuration.token, enableCoroutines = true) {
            intents += listOf(GatewayIntent.GUILD_MEMBERS)
            setAutoReconnect(true)
            setActivity(Activity.watching("justaven.xyz"))
            addEventListeners(Listener)
            setMemberCachePolicy(MemberCachePolicy.ALL)
        }
    }

    private fun checkConfigFile(): Boolean {
        val file = File("config.yml")
        if (!file.exists())
        {
            logger.error("config.yml missing!")
            return false
        }

        return true
    }
}