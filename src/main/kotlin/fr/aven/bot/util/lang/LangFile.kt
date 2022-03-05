package fr.aven.bot.util.lang

import com.google.gson.JsonObject
import fr.aven.bot.GSON
import java.io.File

/**
 * Manage lang files
 * @property lang [String] lang code
 * @property data [JsonObject] lang data
 */
class LangFile(private val lang: String, data: JsonObject) {
    var data: JsonObject = data
        set(value) {
            field = value
            save()
        }

    private fun save() = File("lang/$lang.json").writeText(GSON.toJson(data))

    companion object {
        private val LANG_DIR = File("./lang/")
        init { LANG_DIR.mkdirs() }

        /**
         * Load lang file
         * @param lang [String] lang code
         */
        fun load(lang: String): LangFile {
            val file = File(LANG_DIR, "$lang.json")
            if (!file.exists()) file.createNewFile()
            val data = GSON.fromJson(file.readText(), JsonObject::class.java) ?: JsonObject()
            return LangFile(lang, data)
        }
    }
}