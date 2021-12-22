package fr.aven.bot.commands.`fun`

import com.google.gson.Gson
import fr.aven.bot.Main
import net.dv8tion.jda.api.entities.TextChannel
import net.dv8tion.jda.api.entities.VoiceChannel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.json.JSONTokener
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

data class Data(val max_age: Int, val max_uses: Int, val target_application_id: String, val target_type: Int, val temporary: Boolean, val validate: JvmType.Object?)

class Activity
{
    private val client = OkHttpClient().newBuilder().build()

    fun getActivityLink(type: String, channel: VoiceChannel): String {
        val mediaType = "application/json".toMediaTypeOrNull()

        val appID = if (type == "youtube") "880218394199220334" else if (type == "poker") "755827207812677713" else if (type == "fishington") "814288819477020702" else "773336526917861400"

        val data = Data(86400,0, appID, 2, false, null)
        val body: RequestBody = Gson().toJson(data).toRequestBody(mediaType)
        val request: Request =
            Request.Builder().url("https://discord.com/api/v8/channels/${channel.id}/invites").method("POST", body)
                .addHeader("Authorization", "Bot ${Main.getConfiguration().getString("token")}").addHeader("Content-Type", "application/json").build()

        val response = client.newCall(request).execute()
        val responseBody = response.body
        val tokener = JSONTokener(responseBody!!.charStream())
        val obj = JSONObject(tokener)
        val returned = "<https://discord.gg/" + obj["code"] + ">"
        response.close()

        return returned
    }
}