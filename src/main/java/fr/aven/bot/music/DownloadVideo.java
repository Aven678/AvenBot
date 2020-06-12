package fr.aven.bot.music;

import java.io.File;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadVideo
{
    public static void downloadVideo(String url)
    {
        String download_path="/var/www/music";
        String[] command =
                {
                        "/bin/bash",
                };
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
            PrintWriter stdin = new PrintWriter(p.getOutputStream());
            stdin.println("cd \""+download_path+"\"");
            stdin.println("youtube-dl --extract-audio --audio-format mp3 "+url+" --output \""+url.replace("https://www.youtube.com/watch?v=", "")+".%(ext).s\"");
            stdin.close();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
