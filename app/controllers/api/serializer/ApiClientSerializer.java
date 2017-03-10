package controllers.api.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import models.ApiClient;
import models.ApiCompany;
import services.CompanyService;

import java.lang.reflect.Type;

public class ApiClientSerializer implements JsonSerializer<ApiClient> {

    private static ApiClientSerializer instance;

    private ApiClientSerializer() {}

    public static ApiClientSerializer getInstance() {
        if (instance == null) {
            instance = new ApiClientSerializer();
        }
        return instance;
    }

    @Override
    public JsonElement serialize(ApiClient item, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject json = new JsonObject();
        json.addProperty("name", item.name);
        json.addProperty("publicKey", item.apiKey);
        json.addProperty("privateKey", item.apiPrivateKey);
        json.add("company", ApiCompanySerializer.getInstance().serialize(item.company, ApiCompany.class, jsonSerializationContext));
        return json;
    }

}
