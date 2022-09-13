package fr.aven.bot.commands.list.slash.`fun`

import fr.aven.bot.commands.ISlashCmd
import fr.aven.bot.util.lang.LangManager
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData
import net.dv8tion.jda.api.interactions.commands.build.Commands
import org.json.JSONObject
import java.net.URL

class Cat: ISlashCmd {
    override val description: String
        get() = "Send cat's random image"

    override suspend fun action(event: SlashCommandInteractionEvent, lang: LangManager) {
        event.interaction.deferReply().queue()
        val json = JSONObject(URL("https://aws.random.cat/meow").readText())
        event.interaction.hook.editOriginal(json.getString("file")).queue()
    }

    override val data: CommandData
        get() = Commands.slash(name, description)
    override val name: String
        get() = "cat"
}