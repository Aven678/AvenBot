package fr.aven.bot.util;

import fr.aven.bot.Main;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class Configuration
{
    private final JSONObject object;
    private final File file;

    public Configuration(String path) throws IOException
    {
        this.file = new File(path);
        if (file.exists())
            this.object = new JSONReader(file).toJSONObject();
        else
            this.object = new JSONObject();
    }

    public boolean has(String key)
    {
        return object.has(key);
    }

    public Object get(String key)
    {
        return object.has(key) ? object.get(key) : null;
    }

    public <T> T getOrSetDefault(String key, T defaultValue)
    {
        if(!has(key))
            set(key, defaultValue);
        return (T) get(key);
    }

    public String getString(String key, String defaultString)
    {
        String str = getString(key);
        if(str == null) object.put(key, defaultString == null ? "" : defaultString);
        return object.getString(key);
    }

    public int getInt(String key, int defaultInt)
    {
        int i = getInt(key);
        if(i == Integer.MAX_VALUE) object.put(key, defaultInt);
        return object.getInt(key);
    }

    public String getString(String key)
    {
        return object.has(key) ? object.getString(key) : null;
    }

    public int getInt(String key)
    {
        return object.has(key) ? object.getInt(key) : Integer.MAX_VALUE;
    }

    public void set(String key, Object object)
    {
        if(object == null)
            this.object.remove(key);
        else
            this.object.put(key, object);
    }

    public long getLong(String key)
    {
        return object.has(key) ? object.getLong(key) : 0L;
    }

    public long getLong(String key, long defaultInt)
    {
        long i = getLong(key);
        if(i == 0L) object.put(key, defaultInt);
        return object.getLong(key);
    }

    public void save()
    {
        try (JSONWriter writer = new JSONWriter(file)) {
            writer.write(object);
            writer.flush();
        } catch (IOException e) {
            Main.logger.error(e.getLocalizedMessage());
        }
    }
}
