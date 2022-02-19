package fr.aven.bot.commands.music

import fr.aven.bot.commands.CommandManager
import fr.aven.bot.commands.ICommand
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.Commands.slash
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData

class Play(private val manager: CommandManager): ICommand
{
    override suspend fun action(event: SlashCommandInteractionEvent) {
        if (event.member!!.voiceState?.channel == null) event.interaction.hook.editOriginal(manager.language.getTextFor(event.guild!!, "join.isNotInChannel"))

        event.guild!!.audioManager.openAudioConnection(event.member!!.voiceState?.channel)
        manager.playerManager.loadAndPlay(event, event.getOption("song")!!.asString)
    }

    override val name: String
        get() = "play"

    override val description: String
        get() = "Plays a song"
}