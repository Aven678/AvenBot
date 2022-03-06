package fr.aven.bot.util.lang

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.github.reactivecircus.cache4k.Cache
import java.util.*
import kotlin.time.Duration.Companion.hours

/**
 * Manage lang
 * @property lang [String] lang
 */
class LangManager(private val lang: String) {
    private val internalLangCache = Cache.Builder().expireAfterWrite(6.hours).build<LangManager, LangFile>()

    init {
        refreshCacheLangFile(LangFile.load(lang))
    }

    private fun refreshCacheLangFile(langFile: LangFile): LangFile {
        internalLangCache.put(this, langFile)
        return langFile
    }

    private fun getLangFile(): LangFile = internalLangCache.get(this) ?: refreshCacheLangFile(LangFile.load(lang))

    /**
     * Get a [String] from the lang file
     * @param key [String] key
     * @param default [String] default value
     *
     * @return [String] value
     */
    fun getString(key: LangKey, default: String): String = getString(key.toString(), default)
    private fun getString(key: String, default: String): String {
        val langFile = getLangFile()

        fun createMissingKeys(): String {
            val missing = key.split(".")
            val newObject = langFile.data
            val checked: MutableList<String> = mutableListOf()
            var current = newObject
            missing.forEach {
                checked.add(it)
                current = if (current.has(it) && current.get(it) is JsonObject) current.getAsJsonObject(it)
                else {
                    if (missing.indexOf(it) == missing.size - 1) return@forEach current.addProperty(it, default)
                    current.add(it, JsonObject())
                    current.getAsJsonObject(it)
                }
            }
            langFile.data = newObject
            return default
        }

        val queue: LinkedList<String> = LinkedList(key.split("."))

        var currentElement: Any = langFile.data
        for (i in 0 until queue.size) {
            val current = queue.poll()
            if (currentElement !is JsonObject) continue
            if (currentElement.has(current)) {
                val element = currentElement.get(current)
                if (element is JsonObject && queue.isEmpty()) return createMissingKeys()
                else currentElement = element
            } else return createMissingKeys()
        }
        currentElement as JsonElement
        return currentElement.asString
    }
}



