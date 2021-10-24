package fr.aven.bot.modules.music.lyrics;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.Normalizer;

public class LyricsAPI
{
    public static final String domain = "genius.com";
    public static String USER_AGENT = "Mozilla/5.0 (Linux; U; Android 6.0.1; ko-kr; Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";

    public static Lyrics search(String query) {
        query = Normalizer.normalize(query, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        JsonObject response = null;
        try {
            URL queryURL = new URL(String.format("https://evan.lol/lyrics/search/top?q=%s", URLEncoder.encode(query, "UTF-8")));
            Connection connection = Jsoup.connect(queryURL.toExternalForm())
                    .ignoreContentType(true);
            Document document = connection.userAgent(USER_AGENT).get();
            response = JsonParser.parseString(document.text()).getAsJsonObject();

        } catch (JsonSyntaxException | IOException e) {
            var lst = GeniusAPI.search(query);

            return lst.isEmpty() ? null : lst.get(0);
        }

        if (response == null)
        {
            var lst = GeniusAPI.search(query);
            return lst.isEmpty() ? null : lst.get(0);
        }

        Lyrics l = new Lyrics(Lyrics.SEARCH_ITEM);
        JsonArray artists = response.getAsJsonArray("artists");
        l.setArtist(artists.getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString());
        l.setTitle(response.get("name").getAsString());
        l.setCoverURL(response.getAsJsonObject("album").getAsJsonObject("icon").get("url").getAsString());
        l.setText(response.get("lyrics").getAsString());
        l.setSource("evan.lol");

        return l;
    }

    public static Lyrics search(String artist, String name, String coverURL) {
        JsonObject response;
        try {
            URL queryURL = new URL(String.format("https://api.lyrics.ovh/v1/%s/%s", URLEncoder.encode(artist, "UTF-8"), URLEncoder.encode(name, "UTF-8")));
            Connection connection = Jsoup.connect(queryURL.toExternalForm())
                    .ignoreContentType(true);
            Document document = connection.userAgent(USER_AGENT).get();
            response = JsonParser.parseString(document.text()).getAsJsonObject();

        } catch (JsonSyntaxException | IOException e) {
            return search(artist+" "+name);
        }

        if (response == null)
            return search(artist+" "+name);

        Lyrics l = new Lyrics(Lyrics.SEARCH_ITEM);
        l.setArtist(artist);
        l.setTitle(name);
        l.setCoverURL(coverURL);
        l.setText(response.get("lyrics").getAsString().replaceFirst(".+\r", ""));
        l.setSource("lyrics.ovh");

        return l;
    }
}
