package fr.aven.bot.commands.music

import dev.minn.jda.ktx.interactions.Option
import fr.aven.bot.commands.CommandManager
import fr.aven.bot.commands.ICommand
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData
import java.net.MalformedURLException
import java.net.URL

class Play(private val manager: CommandManager): ICommand
{
    override suspend fun action(event: SlashCommandInteractionEvent) {
        if (event.member!!.voiceState?.channel == null) event.interaction.hook.editOriginal(manager.language.getTextFor(event.guild!!, "join.isNotInChannel"))

        val memberChannel = event.member!!.voiceState?.channel!!
        if (!event.guild!!.audioManager.isConnected) {
            if (!event.guild!!.selfMember.hasPermission(memberChannel, Permission.VOICE_CONNECT))
            {
                event.interaction.hook.editOriginal(manager.language.getTextFor(event.guild!!, "")).queue()
                return
            }

            event.guild!!.audioManager.openAudioConnection(memberChannel)
            event.guild!!.audioManager.isSelfDeafened = true
        }
        else if (event.guild!!.audioManager.connectedChannel!!.id != event.member!!.voiceState!!.channel!!.id)
        {
            event.interaction.hook.editOriginal(manager.language.getTextFor(event.guild!!, "stop.isNotInSameChannel")).queue()
            return
        }

        var request = event.getOption("song")!!.asString

        if (!isUrl(event.getOption("song")!!.asString)) request = "ytsearch:$request"
        manager.playerManager.loadAndPlay(event, request)
    }

    private fun isUrl(str: String): Boolean {
        try {
            URL(str)
            return true
        } catch (_: MalformedURLException) {}

        return false
    }

    override val name: String
        get() = "play"

    override val description: String
        get() = "Plays a song"

    override val options: List<OptionData>
        get() = listOf(Option<String>("song", "URL or title to search in YouTube", true))
}