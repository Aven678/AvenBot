package fr.aven.bot.util

import com.google.gson.Gson
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.json.JSONTokener

data class InviteData(val max_age: Int, val max_uses: Int, val target_application_id: String, val target_type: Int, val temporary: Boolean, val validate: Any?)

class Activities
{
    companion object {
        private val client: OkHttpClient = OkHttpClient().newBuilder().build()
        private val mediaType: MediaType = "application/json".toMediaType()

        fun createInvite(request: String, channel: AudioChannel): String {
            val appId = when(request) {
                "betrayal" -> "773336526917861400"
                "fishington" -> "814288819477020702"
                "poker" -> "755827207812677713"
                "youtube" -> "880218394199220334"
                else -> "null"
            }

            val data = InviteData(86400,0, appId, 2, false, null)
            val body = Gson().toJson(data).toRequestBody(mediaType)
            val request = Request.Builder().url("https://discord.com/api/v9/channels/${channel.id}/invites").method("POST", body)
                .addHeader("Authorization", "Bot sussy").addHeader("Content-Type", "application/json").build()

            val res = client.newCall(request).execute()
            val obj = JSONObject(JSONTokener(res.body!!.charStream()))
            val invite = "<https://discord.gg/${obj["code"]}>"
            res.close()

            return invite
        }
    }
}