package fr.aven.bot.util;

import fr.aven.bot.Main;
import net.explodingbush.ksoftapi.KSoftAPI;
import net.explodingbush.ksoftapi.entities.Lyric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KSoft
{
    private KSoftAPI kSoftAPI = null;

    public KSoftAPI getKSoftAPI()
    {
        if (kSoftAPI == null)
            kSoftAPI = new KSoftAPI(Main.getConfiguration().getString("ksoft-api", "6272b057973847dc8dca241a9ced9213d35e8963"));

        return kSoftAPI;
    }

    public Lyric getLyrics(String query)
    {
        List<Lyric> lyrics = getKSoftAPI().getLyrics().search(query).execute();

        if (lyrics.isEmpty()) return null;
        return lyrics.get(0);
    }

    public List<Lyric> getLyricsList(String query)
    {
        List<Lyric> lyrics = getKSoftAPI().getLyrics().search(query).execute();

        if (lyrics.isEmpty()) return new ArrayList<>();

        return lyrics;
    }
}
