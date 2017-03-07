package controllers.api.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.City;
import models.Country;

import java.lang.reflect.Type;

public class CitySerializer implements JsonSerializer<City> {

    private static CitySerializer instance;

    private CitySerializer() {}

    public static CitySerializer getInstance() {
        if (instance == null) {
            instance = new CitySerializer();
        }
        return instance;
    }

    @Override
    public JsonElement serialize(City item, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json = new JsonObject();
        json.addProperty("uniq", item.uniq);
        json.addProperty("name", item.name);
        json.add("country", CountrySerializer.getInstance().serialize(item.country, Country.class, jsonSerializationContext));
        return json;
    }
}
