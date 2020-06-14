package fr.aven.bot.util;

import fr.aven.bot.Main;
import net.explodingbush.ksoftapi.KSoftAPI;
import net.explodingbush.ksoftapi.entities.Lyric;

public class KSoft
{
    private KSoftAPI kSoftAPI = null;

    public KSoftAPI getKSoftAPI()
    {
        if (kSoftAPI == null)
            kSoftAPI = new KSoftAPI(Main.getConfiguration().getString("ksoft-api", ""));

        return kSoftAPI;
    }

    public Lyric getLyrics(String query)
    {
        return getKSoftAPI().getLyrics().search(query).execute().get(0);
    }
}
