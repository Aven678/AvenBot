package fr.aven.bot.commands.music

import dev.minn.jda.ktx.interactions.Option
import fr.aven.bot.commands.CommandManager
import fr.aven.bot.commands.ICommand
import fr.aven.bot.commands.ISlashCmd
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.OptionData

class Volume(private val manager: CommandManager): ISlashCmd
{
    override val name: String
        get() = "volume"
    override val description: String
        get() = "Set the volume"
    override val options: List<OptionData>
        get() = listOf(Option<Int>("volume", "Volume", required = true).setRequiredRange(0, 200))

    override suspend fun action(event: SlashCommandInteractionEvent) {
        val musicManager = manager.playerManager.guildMusicManager(event)

        if (musicManager.player.playingTrack == null)
        {
            event.hook.editOriginal(manager.language.getTextFor(event.guild!!, "queue.playerNotActive")).queue()
            return
        }
        else if (event.guild!!.audioManager.connectedChannel!!.id != event.member!!.voiceState!!.channel!!.id)
        {
            event.interaction.hook.editOriginal(manager.language.getTextFor(event.guild!!, "stop.isNotInSameChannel")).queue()
            return
        }

        val choice = event.getOption("volume")!!.asInt
        if (choice > 200)
        {
            event.interaction.hook.editOriginal(manager.language.getTextFor(event.guild!!, "volume.exceed")).queue()
            return
        }

        musicManager.player.volume = choice
        event.interaction.hook.editOriginal(manager.language.getTextFor(event.guild!!, "volume.confirm").format(choice)).queue()
    }
}