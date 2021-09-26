package fr.aven.bot.modules.jda.events

import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class PrivateChannels: ListenerAdapter()
{
    override fun onGuildVoiceJoin(event: GuildVoiceJoinEvent) {


        super.onGuildVoiceJoin(event)
    }
}