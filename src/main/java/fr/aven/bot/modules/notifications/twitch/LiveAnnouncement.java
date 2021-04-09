package fr.aven.bot.modules.notifications.twitch;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.events.ChannelGoLiveEvent;
import fr.aven.bot.modules.database.TwitchDB;
import fr.aven.bot.modules.notifications.twitch.entity.TwitchChannel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.util.List;

public class LiveAnnouncement {

    ShardManager shardManager;
    TwitchClient twitchClient;
    String accessToken;
    TwitchDB twitchDB;


    public LiveAnnouncement(SimpleEventHandler eventHandler, ShardManager shardManager, TwitchClient twitchClient, String accessToken, TwitchDB twitchDB)
    {
        this.twitchDB = twitchDB;
        this.shardManager = shardManager;
        this.twitchClient = twitchClient;
        this.accessToken = accessToken;

        eventHandler.onEvent(ChannelMessageEvent.class, event -> {
            System.out.println("j'ai reçu un message OHLALALA sur la chaine "+event.getChannel().getName());
            System.out.println("c'est : "+event.getMessage());
        });

        eventHandler.onEvent(ChannelGoLiveEvent.class, this::goLiveEvent);
    }

    public void goLiveEvent(ChannelGoLiveEvent event)
    {
        System.out.println("LIVE "+event.getChannel().getName());

        List<TwitchChannel> channels = twitchDB.getAllChannels();
        if (channels.isEmpty()) return;

        channels.forEach(twitchChannel -> {
            String msg = twitchChannel.getMessage().replace("[streamer]", event.getChannel().getName()).replace("[link]", "https://twitch.tv/"+event.getChannel().getName());

            MessageBuilder builder = new MessageBuilder();
            builder.setContent(msg);
            builder.setEmbed(new EmbedBuilder()
                    .setAuthor(event.getChannel().getName(),"https://twitch.tv/"+event.getChannel().getName())
                    .addField("Game", event.getStream().getGameName(), true)
                    .addField("Viewers", event.getStream().getViewerCount()+"", true)
                    .setImage(event.getStream().getThumbnailUrl()).build());

            twitchChannel.getDiscordChannel().sendMessage(builder.build()).queue();
        });

    }
}
