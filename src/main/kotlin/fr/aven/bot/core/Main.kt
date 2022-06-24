package fr.aven.bot.core

import com.sksamuel.hoplite.ConfigLoader
import com.sksamuel.hoplite.ConfigSource
import dev.minn.jda.ktx.jdabuilder.default
import dev.minn.jda.ktx.jdabuilder.intents
import dev.minn.jda.ktx.util.SLF4J
import fr.aven.bot.commands.CommandManager
import fr.aven.bot.core.database.DBManager
import fr.aven.bot.core.database.DatabaseConfig
import fr.aven.bot.events.ActivitiesListener
import fr.aven.bot.events.CListener
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.MemberCachePolicy
import java.io.File
import kotlin.reflect.KClass

data class Config(
    val token: String,
    val database: DatabaseConfig,

    val ipv6_block: String = "none",
    val ytMail:String = "none",
    val ytPass: String = "none"
)

class Main {
    lateinit var config: Config
    lateinit var jda: JDA
    lateinit var manager: CommandManager
    lateinit var database: DBManager

    private val logger by SLF4J

    init {
        logger.info("Starting bot...")
        if (checkConfigFile()) start()
    }

    private fun start() {
        config = ConfigLoader().loadConfigOrThrow<Config>("config.yml")
        database = DBManager(config.database)
        val listener = CListener(this)

        jda = default(config.token, enableCoroutines = true) {
            intents += listOf(GatewayIntent.GUILD_MEMBERS)
            setAutoReconnect(true)
            setActivity(Activity.watching("justaven.xyz"))
            addEventListeners(listener, ActivitiesListener(this@Main))
            setMemberCachePolicy(MemberCachePolicy.ALL)
        }

        manager = CommandManager(this)
    }

    private fun checkConfigFile(): Boolean {
        val file = File("config.yml")
        if (!file.exists()) {
            logger.error("config.yml missing!")
            return false
        }

        return true
    }
}
