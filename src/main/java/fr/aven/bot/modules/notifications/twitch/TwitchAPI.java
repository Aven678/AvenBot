package fr.aven.bot.modules.notifications.twitch;

import com.github.philippheuer.credentialmanager.CredentialManager;
import com.github.philippheuer.credentialmanager.CredentialManagerBuilder;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.core.EventManager;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.TwitchClientHelper;
import com.github.twitch4j.auth.providers.TwitchIdentityProvider;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.events.ChannelGoLiveEvent;
import com.github.twitch4j.events.ChannelGoOfflineEvent;
import fr.aven.bot.Main;
import fr.aven.bot.modules.database.TwitchDB;
import fr.aven.bot.modules.jda.JDAManager;
import fr.aven.bot.modules.notifications.twitch.entity.TwitchChannel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.MessageBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TwitchAPI
{
    private final TwitchClient twitchClient;
    private final TwitchDB twitchDB;
    private final Logger LOGGER = LoggerFactory.getLogger(TwitchAPI.class);

    private String accessToken;

    public TwitchAPI()
    {
        this.twitchDB = new TwitchDB();
        this.twitchClient = createTwitchClient();
    }

    private TwitchClient createTwitchClient()
    {
        OAuth2Credential oAuth2Credential = new OAuth2Credential("twitch", "oauth:v4g1f98uo31b6ilh5vf5ymvp5abofg");
        TwitchClientBuilder twitchClientBuilder = TwitchClientBuilder.builder();

        CredentialManager credentialManager = CredentialManagerBuilder.builder().build();
        credentialManager.getOAuth2CredentialByUserId(Main.getConfiguration().getString("twitch.clientID", "default"));

        TwitchIdentityProvider provider = new TwitchIdentityProvider(Main.getConfiguration().getString("twitch.clientID", "default"),
                Main.getConfiguration().getString("twitch.clientSecret", "default"), "");

        credentialManager.registerIdentityProvider(provider);

        accessToken = provider.getAppAccessToken().getAccessToken();

        TwitchClient twitchClient = twitchClientBuilder
                .withEnableHelix(true)
                .withCredentialManager(credentialManager)
                .withChatAccount(oAuth2Credential)
                .withEnableChat(true)
                .withDefaultAuthToken(provider.getAppAccessToken())
                .build();

        twitchClient.getEventManager().registerEventHandler(new SimpleEventHandler());
        /*
                .withDefaultEventHandler(SimpleEventHandler.class)
                //.withDefaultAuthToken(new OAuth2Credential("twitch", "oauth:fvzvtu9urq2uqyu8vq3vlbsr7edlfy"))
                .withChatAccount(oAuth2Credential)
                .withEnableChat(true)
                .withEnableHelix(true)
                .withEnableKraken(true)*/
        twitchClient.getClientHelper().enableStreamEventListener("178561694","Aven678");
        /*twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class).onEvent(ChannelGoLiveEvent.class, this::goLiveEvent);/*
        twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class).onEvent(ChannelMessageEvent.class, event -> {
            System.out.println("bon allez j'ai reçu un message");
            LOGGER.info(event.getMessage());
        });

        //twitchClient.getChat().sendMessage("Aven678", "UN JOUR CA FONCTIONNERA CETTE MERDE");
        twitchDB.getAllChannels().forEach(channel -> twitchClient.getClientHelper().enableStreamEventListener(channel.getChannelID(), channel.getChannelName()));*/

        return twitchClient;
    }

    /*public void registerFeatures()
    {
        SimpleEventHandler eventHandler = twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class);

        LiveAnnouncement live = new LiveAnnouncement(eventHandler, JDAManager.getShardManager(), twitchClient, accessToken);
    }*/

    /*public void goLiveEvent(ChannelGoLiveEvent event)
    {
        List<TwitchChannel> channels = twitchDB.getInfosOnlineEvent(event.getChannel());
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

    }*/

    public TwitchClient getTwitchClient() {
        return twitchClient;
    }

    public TwitchClientHelper getTwitchClientHelper()
    {
        return twitchClient.getClientHelper();
    }
}
