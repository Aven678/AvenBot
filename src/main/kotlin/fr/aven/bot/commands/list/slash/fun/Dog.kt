package fr.aven.bot.commands.list.slash.`fun`

import fr.aven.bot.commands.ISlashCmd
import fr.aven.bot.util.lang.LangManager
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands
import org.json.JSONObject
import java.net.URL

class Dog: ISlashCmd {
    override val description: String
        get() = "Send dog's random image"

    override suspend fun action(event: SlashCommandInteractionEvent, lang: LangManager) {
        event.interaction.deferReply().queue()
        val json = JSONObject(URL("https://random.dog/woof.json").readText())
        event.interaction.hook.editOriginal(json.getString("url")).queue()
    }

    override val data: CommandData
        get() = Commands.slash(name, description)
    override val name: String
        get() = "dog"
}