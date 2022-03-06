package fr.aven.bot.commands.list.slash.music

import fr.aven.bot.commands.CommandManager
import fr.aven.bot.commands.ISlashCmd
import fr.aven.bot.util.lang.LangKey
import fr.aven.bot.util.lang.LangManager
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands

/**
 * Set volume of the music player.
 */
class Volume: ISlashCmd
{
    override val name: String
        get() = "volume"
    override val description: String
        get() = "Set the volume"
    override val data: CommandData
        get() = Commands.slash(name, description)
            .addOption(OptionType.INTEGER, "volume", "Volume to set", true)

    override suspend fun action(event: SlashCommandInteractionEvent, lang: LangManager) {
        val musicManager = CommandManager.playerManager.guildMusicManager(event)
        val guild = event.guild ?: throw IllegalStateException("Guild is null")

        musicManager.player.playingTrack ?: return event.reply(lang.getString(LangKey.keyBuilder(this, "action", "playerNotActive"),
            "Aucune piste n'est en cours de lecture !"))
            .setEphemeral(true)
            .queue()

        val connectedChannel = guild.audioManager.connectedChannel ?: return event.reply(lang.getString(LangKey.keyBuilder(this, "action", "notConnected"),
            "Aucune piste n'est en cours de lecture !"))
            .setEphemeral(true)
            .queue()

        if (connectedChannel.id != event.member?.voiceState?.channel?.id) {
            return event.reply(lang.getString(LangKey.keyBuilder(this, "action", "isNotInSameChannel"),
                "Vous devez être connecter dans le même channel que moi pour ajouté des musiques !"))
                .setEphemeral(true)
                .queue()
        }

        val choice = event.getOption("volume")!!.asInt
        if (choice > 200) {
            return event.reply(lang.getString(LangKey.keyBuilder(this, "action", "volumeExceed"),
                "Le volume maximum est `200` !"))
                .setEphemeral(true)
                .queue()
        }

        musicManager.player.volume = choice
        event.reply(lang.getString(LangKey.keyBuilder(this, "action", "volumeConfirm"),
            "Le volume a été défini sur %d !").format(choice))
            .setEphemeral(true)
            .queue()
    }
}
