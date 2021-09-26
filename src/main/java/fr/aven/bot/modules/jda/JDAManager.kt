package fr.aven.bot.modules.jda

import com.sedmelluq.discord.lavaplayer.jdaudp.NativeAudioSendFactory
import fr.aven.bot.Main
import fr.aven.bot.modules.jda.events.*
import fr.aven.bot.modules.tickets.TicketsEvent
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder
import net.dv8tion.jda.api.sharding.ShardManager
import net.dv8tion.jda.api.utils.ChunkingFilter
import net.dv8tion.jda.api.utils.MemberCachePolicy

object JDAManager
{
    @JvmStatic
    var manager: ShardManager = this.buildManager().build()

    private fun buildManager(): DefaultShardManagerBuilder {
        return DefaultShardManagerBuilder
            .createDefault(Main.getConfiguration().getString("token"))
            .enableIntents(GatewayIntent.GUILD_MEMBERS)
            .setAutoReconnect(true)
            .setMemberCachePolicy(MemberCachePolicy.ALL)
            .setChunkingFilter(ChunkingFilter.ALL)
            .setAudioSendFactory(NativeAudioSendFactory())
            .addEventListeners(TicketsEvent(), BingoListener(), AFKListener(), ReactRoleListener(), GuildActivities(), InteractionListener())
    }

    @JvmStatic
    fun getShardManager(): ShardManager { return manager}

}