package fr.aven.bot.music;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.model_objects.specification.Track;
import fr.aven.bot.Main;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SpotifyAPI
{
    private SpotifyApi api;
    private Logger LOGGER = LoggerFactory.getLogger(SpotifyApi.class);
    private ClientCredentials clientCredentials;

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

        try {
            clientCredentials = api.clientCredentials().build().execute();
            api.setAccessToken(clientCredentials.getAccessToken());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SpotifyWebApiException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LOGGER.info("Spotify connected.");
    }

    public SpotifyApi getApi() {
        if (clientCredentials.getExpiresIn() == 0)
        {
            try {
                clientCredentials = api.clientCredentials().build().execute();
                api.setAccessToken(clientCredentials.getAccessToken());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SpotifyWebApiException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return api;
    }

    public Paging<PlaylistTrack> getPlaylistTracks(String url)
    {
        try {
            Path path = Paths.get(new URL(url).getPath());

            String playlist = path.getName(1).toString();
            return getApi().getPlaylistsItems(playlist).build().execute();
        }
        catch (IOException | SpotifyWebApiException | ParseException e)
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
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
