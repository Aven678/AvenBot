package fr.aven.bot.commands.list.buttons

import fr.aven.bot.commands.CommandManager
import fr.aven.bot.commands.IButtonCmd
import fr.aven.bot.util.lang.LangManager
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent

/**
 * Buttons musique commands.
 */
class MusicButtons: IButtonCmd {
    override suspend fun action(event: ButtonInteractionEvent, lang: LangManager) {
        event.deferEdit().queue()
        val guildMusicManager = CommandManager.playerManager.guildMusicManager(event)

        with(event.button.id){
            when {
                equals("m.old") -> guildMusicManager.scheduler.playBackTrack()
                equals("m.player") -> guildMusicManager.scheduler.changePlayerStatus()
                equals("m.skip") -> guildMusicManager.scheduler.skipTrack()
                equals("m.lyrics") -> TODO()
                equals("m.repeatPlaylist") -> guildMusicManager.scheduler.repeatPlaylist()
                equals("m.repeatTrack") -> guildMusicManager.scheduler.repeatTrack()
                equals("m.stop") -> guildMusicManager.scheduler.stopPlayer()

                this?.startsWith("m.queue") == true -> guildMusicManager.sendQueueMessage(event)

                else -> throw IllegalArgumentException("Button id not found")
            }
        }
    }

    override val name: String
        get() = "m."
}