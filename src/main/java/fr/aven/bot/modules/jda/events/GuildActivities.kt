package fr.aven.bot.modules.jda.events

import fr.aven.bot.util.MessageUtil
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class GuildActivities : ListenerAdapter() {

    override fun onGuildJoin(event: GuildJoinEvent) {
        val embed = EmbedBuilder()
        embed.setColor(MessageUtil.getRandomColor())
        embed.setAuthor("New guild joined", "https://www.justaven.xyz", event.jda.selfUser.avatarUrl)
        embed.addField("Guild name", event.guild.name, false)
        embed.addField("Members", event.guild.members.size.toString(), true)
        embed.addField("Owner", event.guild.owner?.user?.asTag, true)

        event.jda.getTextChannelById(412908508590243840L)?.sendMessage(embed.build())?.queue()
        super.onGuildJoin(event)
    }

    override fun onGuildLeave(event: GuildLeaveEvent) {
        val embed = EmbedBuilder()
        embed.setColor(MessageUtil.getRandomColor())
        embed.setAuthor("Guild leaved", "https://www.justaven.xyz", event.jda.selfUser.avatarUrl)
        embed.addField("Guild name", event.guild.name, false)
        embed.addField("Members", event.guild.members.size.toString(), true)

        event.jda.getTextChannelById(412908508590243840L)?.sendMessage(embed.build())?.queue()

        super.onGuildLeave(event)
    }

}