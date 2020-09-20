package fr.aven.bot.jda;

import com.sedmelluq.discord.lavaplayer.jdaudp.NativeAudioSendFactory;
import fr.aven.bot.Main;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import java.util.EnumSet;
import java.util.function.Function;

public class JDAManager
{
    private static ShardManager shardManager = createShardManager();

    private static ShardManager createShardManager()
    {
        try
        {
            System.out.println(Main.getConfiguration().getString("token", "Insert your token here."));
            return DefaultShardManagerBuilder.createDefault(Main.getConfiguration().getString("token", "Insert your token here."))
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .enableIntents(EnumSet.allOf(GatewayIntent.class))
                    .setAudioSendFactory(new NativeAudioSendFactory())
                    .build();
        } catch (Exception e)
        {
            Main.getConfiguration().save();
            Main.LOGGER.error("Token erroné.");
        }

        return null;
    }

    public static ShardManager getShardManager() { return shardManager; }
}

