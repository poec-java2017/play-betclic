package controllers.api.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.Address;
import models.Operation;
import models.OperationType;
import models.User;

import java.lang.reflect.Type;

public class OperationSerializer implements JsonSerializer<Operation> {

    private static OperationSerializer instance;

    private OperationSerializer(){}

    public static OperationSerializer getInstance() {
        if (instance == null) {
            instance = new OperationSerializer();
        }
        return instance;
    }

    @Override
    public JsonElement serialize(Operation item, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json = new JsonObject();
        json.addProperty("uniq", item.uniq);
        json.addProperty("date", item.date.toString());
        json.addProperty("amount", item.amount);
        json.add("type", OperationTypeSerializer.getInstance().serialize(item.operationType, OperationType.class, jsonSerializationContext));
        json.addProperty("user", item.user.uniq);
        return json;
    }
}
