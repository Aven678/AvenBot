package fr.aven.bot.commands.music

import fr.aven.bot.commands.CommandManager
import fr.aven.bot.commands.ISlashCmd
import fr.aven.bot.util.lang.LangKey
import fr.aven.bot.util.lang.LangManager
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands

/**
 * Musique play command
 */
class Play(private val manager: CommandManager): ISlashCmd
{
    override val name: String
        get() = "play"
    override val description: String
        get() = "Plays a song"

    override val data: CommandData
        get() = Commands.slash(name, description)
            .addOption(OptionType.STRING, "song", "URL or title to search in YouTube", true)

    override suspend fun action(event: SlashCommandInteractionEvent, lang: LangManager) {
        val memberChannel = event.member?.voiceState?.channel ?: return event
            .reply(lang.getString(LangKey.keyBuilder(this, "action", "isNotInChannel"),
                "Vous devez être connecter a un channel vocal pour utiliser cette commande !"))
            .setEphemeral(true)
            .queue()
        val guild = event.guild ?: throw IllegalStateException("Guild is null")

        if (!guild.audioManager.isConnected) {
            if (guild.selfMember.hasPermission(memberChannel, Permission.VOICE_CONNECT))
                return event.reply(lang.getString(LangKey.keyBuilder(this, "action", "missingPermission"),
                    "Vous devez être connecter a un channel vocal pour utiliser cette commande !"))
                    .setEphemeral(true)
                    .queue()
            guild.audioManager.openAudioConnection(memberChannel)
            guild.audioManager.isSelfDeafened = true
        }

        else if (guild.audioManager.connectedChannel?.id != memberChannel.id) {
            return event.reply(lang.getString(LangKey.keyBuilder(this, "action", "isNotInSameChannel"),
                "Vous devez être connecter dans le même channel que moi pour ajouté des musiques !"))
                .setEphemeral(true)
                .queue()
        }
        var request = event.getOptionsByName("song").first().asString

        if (!isUrl(request)) request = "ytsearch:$request"
        manager.playerManager.loadAndPlay(event, request)
    }

    private fun isUrl(str: String): Boolean {
        val regex = "^(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"
        return str.matches(regex.toRegex())
    }
}


/*
        return try {
            URL(str)
            true
        } catch (_: MalformedURLException) { false }
* */