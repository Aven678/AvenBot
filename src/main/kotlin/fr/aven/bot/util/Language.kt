package fr.aven.bot.util

import com.google.gson.Gson
import net.dv8tion.jda.api.entities.Guild
import java.io.InputStream
import java.io.InputStreamReader

@Deprecated("Use fr.aven.bot.util.LangLoader")
class Language
{
    private var folder = "lang/"
    private var languagesFilesMap: Map<String, Any> = HashMap()
    private var languagesMap: MutableMap<String, Any> = mutableMapOf()

    init {
        var inputStream: InputStream = javaClass.classLoader.getResourceAsStream(folder+"langs.json")!!
        val gson = Gson()
        languagesFilesMap = gson.fromJson(InputStreamReader(inputStream, Charsets.UTF_8), languagesFilesMap.javaClass)

        languagesFilesMap.forEach {
            inputStream = javaClass.classLoader.getResourceAsStream(folder+it.value)!!
            languagesMap[it.key] = gson.fromJson(InputStreamReader(inputStream, Charsets.UTF_8), languagesMap.javaClass)

        }
    }

    fun getTextFor(guild: Guild, request: String): String
    {
        return getText(guild.id, request)
    }

    fun getTextFor(guildID: String, request: String): String
    {
        return getText(guildID, request)
    }

    fun getText(guild: Guild, request: String): String
    {
        return getText(guild.id, request)
    }

    fun getText(guildID: String, request: String): String
    {
        val lang = getGuildLang(guildID)
        lateinit var text: String

        if (request.contains("."))
        {
            var requestSplit = request.split(".")
            text = ((languagesMap[lang] as MutableMap<*,*>) [requestSplit[0]] as MutableMap<*,*>) [requestSplit[1]] as String
        }
        else text = (languagesMap[lang] as MutableMap<*,*>) [request] as String

        return text
    }

    private fun getGuildLang(guildID: String): String
    {
        return "en"
    }
}