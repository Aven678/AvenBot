package fr.aven.bot.core

import com.sksamuel.hoplite.ConfigLoader
import dev.minn.jda.ktx.SLF4J
import dev.minn.jda.ktx.cache
import dev.minn.jda.ktx.default
import dev.minn.jda.ktx.intents
import fr.aven.bot.commands.CommandManager
import fr.aven.bot.events.Listener
import fr.aven.bot.util.Language
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.MemberCachePolicy
import java.io.File

data class Config(
    val token: String,
    val firebaseUrl: String,

    val ipv6_block: String = "none"
)

class Main
{
    lateinit var config: Config
    lateinit var jda: JDA
    lateinit var manager: CommandManager

    private val logger by SLF4J

    val language = Language()
    lateinit var firebase: Firebase

    init {
        logger.info("Starting bot...")
        if (checkConfigFile()) start()
    }

    private fun start() {
        config = ConfigLoader().loadConfigOrThrow<Config>(File("config.yml"))
        firebase = Firebase(config)
        val listener = Listener(this)

        jda = default(config.token, enableCoroutines = true) {
            intents += listOf(GatewayIntent.GUILD_MEMBERS)
            setAutoReconnect(true)
            setActivity(Activity.watching("justaven.xyz"))
            addEventListeners(listener)
            setMemberCachePolicy(MemberCachePolicy.ALL)
        }

        manager = CommandManager(this)
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