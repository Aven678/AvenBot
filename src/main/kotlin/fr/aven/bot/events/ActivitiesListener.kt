package fr.aven.bot.events

import dev.minn.jda.ktx.events.CoroutineEventListener
import dev.minn.jda.ktx.messages.Embed
import dev.minn.jda.ktx.util.SLF4J
import fr.aven.bot.core.JDA
import fr.aven.bot.core.Main
import fr.aven.bot.core.database.structures.GuildConfig
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.GenericEvent
import net.dv8tion.jda.api.events.guild.GuildBanEvent
import net.dv8tion.jda.api.events.guild.GuildJoinEvent
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent
import java.util.Date

class ActivitiesListener(private val main: Main): CoroutineEventListener
{
    private val log by SLF4J

    override suspend fun onEvent(event: GenericEvent) {
        when (event) {
            is GuildMemberJoinEvent -> onJoinMemberEvent(event)
            is GuildMemberRemoveEvent -> onRemoveMemberEvent(event)
            is GuildBanEvent -> onBanEvent(event)
            is GuildJoinEvent -> onJoinEvent(event)
            is GuildLeaveEvent -> guildEventMessage(event.guild, false)
        }
    }

    private fun guildEventMessage(guild: Guild, joined: Boolean)
    {
        log.info("${guild.name} ${if (joined) "joined" else "leaved"}")

        JDA.getTextChannelById(412908508590243840)?.sendMessageEmbeds(Embed {
            author {
                name = if (joined) "New guild joined" else "Guild leaved"
                url = "https://wwww.justaven.xyz"
                iconUrl = JDA.selfUser.avatarUrl
            }

            field {
                name = "Guild name"
                value = guild.name
                inline = true
            }

            field {
                name = "Members"
                value = guild.memberCount.toString()
                inline = true
            }

            field {
                name = "Owners"
                value = guild.owner?.user?.asTag.toString()
                inline = true
            }

            thumbnail = guild.iconUrl
        })?.queue()
    }

    private fun onJoinEvent(event: GuildJoinEvent) {
        guildEventMessage(event.guild, true)

        event.guild.getTextChannelById(event.guild.defaultChannel?.id!!)?.sendMessageEmbeds(
            Embed {
                author {
                    name = "AvenBot"
                    url = "https://discord.gg/ntbdKjv"
                    iconUrl = JDA.selfUser.avatarUrl
                }

                description = "It's a JDA multifonction bot, do /help to have all the commands"

                field {
                    name = "Developed by:"
                    value = "Aven#1000"
                    inline = true
                }

                field {
                    name = "Exists since:"
                    value = "Tue, Sep 26 2017 16:12:02 GMT"
                    inline = true
                }

                field {
                    name = "Support server:"
                    value = "Click on the name"
                    inline = false
                }

                footer {
                    name = "Automatic message"
                }

                timestamp = Date().toInstant()
            }
        )?.queue()
    }

    private fun onJoinMemberEvent(event: GuildMemberJoinEvent) {
        GuildConfig.getGuildConfig(event.guild)?.activities?.join?.let {
            GuildConfig.getGuildConfig(event.guild)?.activities?.channel?.sendMessage(
                it.replace("[guild]", event.guild.name).replace("[member]", event.user.asTag).replace("[number]", event.guild.memberCount.toString())
            )?.queue()
        }
    }

    private fun onRemoveMemberEvent(event: GuildMemberRemoveEvent) {
        event.guild.retrieveBanList().queue {
            for (ban in it)
                if (ban.user.id == event.user.id) return@queue
        }

        GuildConfig.getGuildConfig(event.guild)?.activities?.leave?.let {
            GuildConfig.getGuildConfig(event.guild)?.activities?.channel?.sendMessage(
                it.replace("[guild]", event.guild.name).replace("[member]", event.user.asTag).replace("[number]", event.guild.memberCount.toString())
            )?.queue()
        }
    }

    private fun onBanEvent(event: GuildBanEvent) {
        GuildConfig.getGuildConfig(event.guild)?.activities?.ban?.let {
            GuildConfig.getGuildConfig(event.guild)?.activities?.channel?.sendMessage(
                it.replace("[guild]", event.guild.name).replace("[member]", event.user.asTag).replace("[number]", event.guild.memberCount.toString())
            )?.queue()
        }
    }
}