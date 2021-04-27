package fr.aven.bot.commands.`fun`

import com.google.gson.Gson
import fr.aven.bot.Main
import fr.aven.bot.util.ICommand
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import org.json.JSONTokener
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

data class Data(val max_age: Int, val max_uses: Int, val target_application_id: String, val target_type: Int, val temporary: Boolean, val validate: JvmType.Object?)

class YoutubeTogetherCommand: ICommand
{
    private val client = OkHttpClient().newBuilder().build()

    override fun handle(args: MutableList<String>?, event: GuildMessageReceivedEvent) {
        if (!event.member?.voiceState?.inVoiceChannel()!!) {
            event.channel.sendMessage("Vous devez être dans un salon vocal pour exécuter cette commande").queue()
            return
        }

        val voiceState = event.member!!.voiceState
        val channel = voiceState!!.channel
        val mediaType = "application/json".toMediaTypeOrNull()

        val data = Data(86400,0, "755600276941176913", 2, false, null)
        val body: RequestBody = RequestBody.create(mediaType, Gson().toJson(data))
        val request: Request =
            Request.Builder().url("https://discord.com/api/v8/channels/${channel?.id}/invites").method("POST", body)
                .addHeader("Authorization", "Bot ${Main.getConfiguration().getString("token")}").addHeader("Content-Type", "application/json").build()

        val response = client.newCall(request).execute()
        val responseBody = response.body
        val tokener = JSONTokener(responseBody!!.charStream())
        val obj = JSONObject(tokener)
        val returned = "<https://discord.gg/" + obj["code"] + ">"
        response.close()

        event.channel.sendMessage("Link -> $returned").queue()
    }

    override fun getType(): ICommand.Type {
        return ICommand.Type.FUN
    }

    override fun getPermission(): ICommand.Permission {
        return ICommand.Permission.USER
    }

    override fun getHelp(): MessageEmbed.Field {
        return MessageEmbed.Field("", "", false)
    }

    override fun getInvoke(): String {
        return "youtube"
    }

    override fun haveEvent(): Boolean {
        return false
    }

    override fun onEvent(event: GenericEvent?) {
        TODO("Not yet implemented")
    }

    override fun requiredDiscordPermission(): List<Permission> {
        return listOf(Permission.CREATE_INSTANT_INVITE)
    }
}