package fr.aven.bot

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.aven.bot.core.Main
import fr.aven.bot.util.lang.LangLoader

/**
 * Global variable [GSON]
 */
val GSON: Gson by lazy { GsonBuilder().setPrettyPrinting().serializeNulls().create() }

/**
 * the [LangLoader] global instance
 */
val LANG_LOADER by lazy { LangLoader() }

/**
 * Main function
 */
fun main() {
    Main()
}