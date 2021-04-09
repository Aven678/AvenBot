package fr.aven.bot.modules.notifications.twitch;

import com.github.philippheuer.credentialmanager.CredentialManager;
import com.github.philippheuer.credentialmanager.CredentialManagerBuilder;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.auth.providers.TwitchIdentityProvider;
import fr.aven.bot.Main;
import fr.aven.bot.modules.database.TwitchDB;
import fr.aven.bot.modules.jda.JDAManager;

public class Twitch {

    public TwitchClient twitchClient;
    private TwitchDB twitchDB;

    public String accessToken;

    public Twitch(){
        this.twitchDB = new TwitchDB();
        //System.out.println("TwitchApi");
        OAuth2Credential oAuth2Credential = new OAuth2Credential("twitch", "oauth:v4g1f98uo31b6ilh5vf5ymvp5abofg");
        TwitchClientBuilder clientBuilder = TwitchClientBuilder.builder();

        CredentialManager credentialManager = CredentialManagerBuilder.builder().build();

        credentialManager.getOAuth2CredentialByUserId(Main.getConfiguration().getString("twitch.clientID", "default"));

        TwitchIdentityProvider provider = new TwitchIdentityProvider(Main.getConfiguration().getString("twitch.clientID", "default"),
                Main.getConfiguration().getString("twitch.clientSecret", "default"), "");
        credentialManager.registerIdentityProvider(provider);

        accessToken = provider.getAppAccessToken().getAccessToken();

        twitchClient = clientBuilder
                .withEnableHelix(true)
                .withCredentialManager(credentialManager)
                .withDefaultAuthToken(provider.getAppAccessToken())
                .withChatAccount(oAuth2Credential)
                .withEnableChat(true)
                .build();

        twitchClient.getEventManager().registerEventHandler(new SimpleEventHandler());
        twitchClient.getClientHelper().enableStreamEventListener("Aven678");
        twitchClient.getChat().joinChannel("Aven678");
    }


    public void registerFeatures(){
        //System.out.println("registerFeatures");
        SimpleEventHandler eventHandler = twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class);

        LiveAnnouncement live = new LiveAnnouncement(eventHandler, JDAManager.getShardManager(), twitchClient, accessToken, twitchDB);
    }
}