package fr.aven.bot.util.lang

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.User
import java.io.File

private val LANG_MANAGERS: MutableMap<String, LangManager> = mutableMapOf()

/**
 * Loads the lang files.
 */
class LangLoader {
    init {
        if (LANG_MANAGERS.isEmpty()) {
            val folder = File("lang")
            folder.mkdirs()
            folder.listFiles()?.forEach {
                val name = it.name.split(".").first()
                LANG_MANAGERS[name] = LangManager(name)
            }
        }
    }

    /**
     * Get langManager by name.
     * @param user [User] user.
     * @param guild [Guild] guild.
     * @return [LangManager] langManager.
     */
    fun getLangManager(user: User? = null, guild: Guild? = null): LangManager {
        val code = getUserLangCode(user?.id) ?: getGuildLangCode(guild?.id) ?: "en"
        if (!LANG_MANAGERS.containsKey(code)) LANG_MANAGERS[code] = LangManager(code)
        return LANG_MANAGERS[code]!!
    }

    fun getGuildLangCode(guildId: String?): String? {
        return null // TODO : get guild lang code on database
    }

    fun getUserLangCode(userId: String?): String? {
        return null // TODO : get user lang code on database
    }

}