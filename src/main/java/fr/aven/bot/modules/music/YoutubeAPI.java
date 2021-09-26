package fr.aven.bot.modules.music;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import fr.aven.bot.Main;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class YoutubeAPI {

    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    public static final JsonFactory JSON_FACTORY = new GsonFactory();

    private static final String PROPERTIES_FILENAME = "youtube.properties";
    private static final long NUMBER_OF_VIDEOS_RETURNED = 5;

    private YouTube youtube;

    public void buildYoutubeClient()
    {
        youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {

            }
        }).setApplicationName("avenbot-youtube-search").build();
    }

    public Iterator<SearchResult> search(String query)
    {

        try {
            YouTube.Search.List search = youtube.search().list(Arrays.asList("id","snippet"));

            String apiKey = Main.getConfiguration().getString("youtube.apikey", "AIzaSyBBeYv2HCgTZAAAxPuj6jQgzR9opQxEmOE");
            search.setKey(apiKey).setQ(query);
            search.setType(Arrays.asList("video"));
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();

            if (searchResultList != null)
                return searchResultList.iterator();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
