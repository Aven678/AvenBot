package fr.aven.bot

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.aven.bot.core.Main

/**
 * Global variable GSON
 */
val GSON: Gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()


fun main(args: Array<String>) {
    Main()
}