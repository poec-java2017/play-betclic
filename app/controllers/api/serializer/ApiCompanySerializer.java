package controllers.api.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.ApiCompany;

import java.lang.reflect.Type;

public class ApiCompanySerializer implements JsonSerializer<ApiCompany> {

    private static ApiCompanySerializer instance;

    private ApiCompanySerializer() {}

    public static ApiCompanySerializer getInstance() {
        if (instance == null) {
            instance = new ApiCompanySerializer();
        }
        return instance;
    }

    @Override
    public JsonElement serialize(ApiCompany item, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json = new JsonObject();
        json.addProperty("name", item.name);
        return json;
    }

}