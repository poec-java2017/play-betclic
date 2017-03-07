package controllers.api.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.Country;

import java.lang.reflect.Type;

public class CountrySerializer implements JsonSerializer<Country> {

    private static CountrySerializer instance;

    private CountrySerializer() {}

    public static CountrySerializer getInstance() {
        if (instance == null) {
            instance = new CountrySerializer();
        }
        return instance;
    }

    @Override
    public JsonElement serialize(Country item, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json = new JsonObject();
        json.addProperty("uniq", item.uniq);
        json.addProperty("name", item.name);
        json.addProperty("iso", item.iso);
        return json;
    }
}
