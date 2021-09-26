package fr.aven.bot.modules.jda;

import com.sedmelluq.discord.lavaplayer.jdaudp.NativeAudioSendFactory;
import fr.aven.bot.Main;
import fr.aven.bot.modules.jda.events.*;
import fr.aven.bot.modules.tickets.TicketsEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class JDAManagerOld
{
    private static ShardManager shardManager = createShardManager();

    private static ShardManager createShardManager()
    {
        try
        {
            //System.out.println(Main.getConfiguration().getString("token", "Insert your token here."));
            return DefaultShardManagerBuilder.createDefault(Main.getConfiguration().getString("token", "Insert your token here."))
                    //.setMemberCachePolicy(MemberCachePolicy.ALL)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .enableCache(CacheFlag.ONLINE_STATUS)
                    .enableIntents(GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                            GatewayIntent.DIRECT_MESSAGE_TYPING,
                            GatewayIntent.DIRECT_MESSAGES,
                            GatewayIntent.GUILD_BANS,
                            GatewayIntent.GUILD_EMOJIS,
                            GatewayIntent.GUILD_INVITES,
                            GatewayIntent.GUILD_MEMBERS,
                            GatewayIntent.GUILD_MESSAGE_REACTIONS,
                            GatewayIntent.GUILD_MESSAGE_TYPING,
                            GatewayIntent.GUILD_MESSAGES,
                            GatewayIntent.GUILD_VOICE_STATES,
                            GatewayIntent.GUILD_PRESENCES)
                    .setAudioSendFactory(new NativeAudioSendFactory())
                    .addEventListeners(new TicketsEvent(), new BingoListener(), new AFKListener(), new ReactRoleListener(), new GuildActivities(), new InteractionListener())
                    .build();
        } catch (Exception e)
        {
            Main.getConfiguration().save();
            //Main.logger.error("Token erroné.");
        }

        return null;
    }

    public static ShardManager getShardManager() { return shardManager; }
}

