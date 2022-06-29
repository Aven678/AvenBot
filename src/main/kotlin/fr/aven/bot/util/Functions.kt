package fr.aven.bot.util

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent

fun SlashCommandInteractionEvent.replyIteract(content: String) = interaction.hook.editOriginal(content)
fun SlashCommandInteractionEvent.replyIteract(message: Message) = interaction.hook.editOriginal(message)
fun SlashCommandInteractionEvent.replyIteract(embeds: MessageEmbed) = interaction.hook.editOriginalEmbeds(embeds)


