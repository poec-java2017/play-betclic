package controllers.api.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.Address;
import models.User;

import java.lang.reflect.Type;

public class UserSerializer implements JsonSerializer<User> {

    private static UserSerializer instance;

    private UserSerializer() {}

    public static UserSerializer getInstance() {
        if (instance == null) {
            instance = new UserSerializer();
        }
        return instance;
    }

    @Override
    public JsonElement serialize(User item, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json = new JsonObject();
        json.addProperty("firstName", item.firstName);
        json.addProperty("lastName", item.lastName);
        json.addProperty("email", item.email);
        json.add("address", AddressSerializer.getInstance().serialize(item.address, Address.class, jsonSerializationContext));
        return json;
    }
}
