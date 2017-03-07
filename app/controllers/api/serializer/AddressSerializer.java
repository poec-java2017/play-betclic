package controllers.api.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.Address;
import models.City;

import java.lang.reflect.Type;

public class AddressSerializer implements JsonSerializer<Address> {

    private static AddressSerializer instance;

    private AddressSerializer(){}

    public static AddressSerializer getInstance() {
        if (instance == null) {
            instance = new AddressSerializer();
        }
        return instance;
    }

    @Override
    public JsonElement serialize(Address item, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json = new JsonObject();
        json.addProperty(" uniq", item.uniq);
        json.addProperty("street", item.street);
        json.addProperty("postCode", item.postCode);
        json.add("city", CitySerializer.getInstance().serialize(item.city, City.class, jsonSerializationContext));
        return json;
    }
}
