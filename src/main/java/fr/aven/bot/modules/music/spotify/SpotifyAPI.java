package fr.aven.bot.modules.music.spotify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import fr.aven.bot.Main;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SpotifyAPI
{
    private SpotifyApi api;
    private Logger LOGGER = LoggerFactory.getLogger(SpotifyApi.class);
    private ClientCredentialsRequest clientCredentialsRequest;
    private ScheduledExecutorService service;

    public SpotifyAPI(String clientID, String clientSecret)
    {
        createSpotifyAPI(clientID, clientSecret);
        this.clientCredentialsRequest = api.clientCredentials().build();

        service = Executors.newScheduledThreadPool(1, r -> new Thread(r, "Spotify-Token-Update-Thread"));
        service.scheduleAtFixedRate(this::updateAccessToken, 0, 1, TimeUnit.HOURS);
    }

    private void updateAccessToken() {
        try {
            Future<ClientCredentials> clientCredentialsFuture = clientCredentialsRequest.executeAsync();
            ClientCredentials clientCredentials = clientCredentialsFuture.get();
            api.setAccessToken(clientCredentials.getAccessToken());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void createSpotifyAPI(String clientID, String clientSecret)
    {
        if (clientID == null)
        {
            LOGGER.error("clientID has not been set.");
            Main.stop();
        }


        if (clientSecret == null) {
            LOGGER.error("clientSecret has not been set.");
            Main.stop();
        }

        this.api = new SpotifyApi.Builder()
                .setClientId(clientID)
                .setClientSecret(clientSecret)
                .build();

        LOGGER.info("Spotify connected.");
    }

    public SpotifyApi getApi() {
        return api;
    }

    public Paging<PlaylistTrack> getPlaylistTracks(String url)
    {
        try {
            Path path = Paths.get(new URL(url).getPath());

            String playlist = path.getName(1).toString();
            return getApi().getPlaylistsItems(playlist).build().execute();
        }
        catch (IOException | ParseException e)
        {
        }
        catch (SpotifyWebApiException e)
        {
            }

        return null;
    }

    public Track getTrack(String url)
    {
        try {
            Path path = Paths.get(new URL(url).getPath());

            String trackId = path.getName(1).toString();
            return getApi().getTrack(trackId).build().execute();
        } catch (IOException | ParseException e) {
        }
        catch (SpotifyWebApiException e)
        {
        }

        return null;
    }
}
