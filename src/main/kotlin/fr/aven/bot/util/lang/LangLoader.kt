package fr.aven.bot.util.lang

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
     * @param name [String] name of the langManager.
     * @return [LangManager] langManager.
     */
    fun getLangManager(name: String): LangManager {
        if (!LANG_MANAGERS.containsKey(name)) LANG_MANAGERS[name] = LangManager(name)
        return LANG_MANAGERS[name]!!
    }
}