package controllers.api;

import controllers.LogManager;
import controllers.api.exception.*;
import controllers.api.serializer.*;
import play.data.validation.Error;
import play.mvc.results.RenderJson;

import java.util.List;

public class ApiManager extends LogManager {

    public static final String PREFIX = "ApiManager";

    protected static void apiContentCreated(Object object) {
        response.status = 201;
        throw new RenderJson(object,
                AddressSerializer.getInstance(),
                ApiClientSerializer.getInstance(),
                CountrySerializer.getInstance(),
                CitySerializer.getInstance(),
                OperationSerializer.getInstance(),
                OperationTypeSerializer.getInstance(),
                UserSerializer.getInstance()
        );
    }

    protected static void apiNoContent(){
        throw new NoContent();
    }

    protected static void apiNotFound(String message){
        throw new NotFound(message);
    }

    protected static void apiNotFoundIfNull(Object object) {
        apiNotFoundIfNull(object, "Item does not exists");
    }

    protected static void apiNotFoundIfNull(Object object, String message) {
        if(object == null) {
            apiNotFound(message);
        }
    }

    protected static void apiBadCredentials(String message) {
        throw new BadCredentials(message);
    }

    protected static void apiBadInput(List<Error> errors) {
        response.status = 406;
        throw new BadInput(errors);
    }

    protected static void apiBadParam(String message) {
        response.status = 406;
        throw new BadParam(message);
    }

    protected static void apiBusinessError(String s) {
        throw new Business(s);
    }
}
