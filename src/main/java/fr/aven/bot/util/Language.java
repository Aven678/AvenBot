package fr.aven.bot.util;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Language
{
    private static final Map<String, Language> LANGUAGE_MAP = new HashMap<>();

    public static void register() throws IOException {
        File folder = new File("languages");
        if (!folder.exists()) folder.mkdir();

        for (File file : folder.listFiles())
        {
            if (!file.getName().endsWith(".json")) continue;
            Language language = new Language(file.getName().replace(".json", ""), new JSONReader(file).toJSONObject());
            LANGUAGE_MAP.put(language.name, language);
        }

        LANGUAGE_MAP.put("EN", new Language("EN", new JSONReader(new InputStreamReader(Language.class.getResourceAsStream("/assets/avenbot/langs/EN.json"))).toJSONObject()));
    }

    public static Language getLanguage(String name)
    {
        Language language = LANGUAGE_MAP.get(name);
        return language != null ? language : getDefaultLanguage();
    }

    public static Language getLanguageOrNull(String name) { return LANGUAGE_MAP.get(name); }

    public static Language getDefaultLanguage()
    {
        return LANGUAGE_MAP.get("EN");
    }

    private final String name;
    private final JSONObject object;
    private final boolean isDefault;

    private Language(String name, JSONObject object) { this(name, object, false); }

    private Language(String name, JSONObject object, boolean isDefault)
    {
        this.name = name;
        this.object = object;
        this.isDefault = isDefault;
    }

    public String getName() { return name; }

    public String get(String path, String... args)
    {
        JSONObject object = this.object;

        String[] paths = path.split("\\.");

        for (int i = 0; i < paths.length -1; i++)
        {
            if (!object.has(paths[i])) return isDefault ? path : getDefaultLanguage().get(path, args);
            Object obj = object.get(paths[i]);
            if (!(obj instanceof JSONObject)) return isDefault ? path : getDefaultLanguage().get(path, args);
            object = (JSONObject) obj;
        }

        if (!object.has(paths[paths.length -1])) return isDefault ? path : getDefaultLanguage().get(path, args);
        Object obj = object.get(paths[paths.length -1]);
        return obj instanceof String ? String.format(obj.toString(), args) : isDefault ? path : getDefaultLanguage().get(path, args);
    }

    public static Collection<Language> getLanguages() { return new ArrayList<>(LANGUAGE_MAP.values()); }

    public static String[] getLanguagesByName()
    {
        return new ArrayList<>(LANGUAGE_MAP.keySet()).toArray(new String[LANGUAGE_MAP.size()]);
    }

    public boolean isDefault() { return isDefault; }
}
