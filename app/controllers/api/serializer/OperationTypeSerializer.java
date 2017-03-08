package controllers.api.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.OperationType;

import java.lang.reflect.Type;

public class OperationTypeSerializer implements JsonSerializer<OperationType> {

    private static OperationTypeSerializer instance;

    private OperationTypeSerializer() {}

    public static OperationTypeSerializer getInstance() {
        if (instance == null) {
            instance = new OperationTypeSerializer();
        }
        return instance;
    }

    @Override
    public JsonElement serialize(OperationType item, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json = new JsonObject();
        json.addProperty("uniq", item.uniq);
        json.addProperty("name", item.name);
        return json;
    }
}
