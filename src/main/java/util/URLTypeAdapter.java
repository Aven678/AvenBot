package util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.net.URL;

public class URLTypeAdapter implements JsonSerializer<URL>, JsonDeserializer<URL> {

    @Override
    public JsonElement serialize(URL url, Type type, JsonSerializationContext jsonSerializationContext) {
        return url == null ? null : new JsonPrimitive(url.toString());
    }

    @Override
    public URL deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        if (jsonElement.isJsonPrimitive()) {
            final JsonPrimitive jsonPrimitive = jsonElement.getAsJsonPrimitive();
            if (jsonPrimitive.isString() && jsonPrimitive.getAsString().isEmpty())
                return null;
        }
        return jsonDeserializationContext.deserialize(jsonElement, type);
    }

}
