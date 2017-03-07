package controllers.api;

import controllers.LogManager;
import controllers.api.exception.*;
import controllers.api.serializer.*;
import play.data.validation.Error;
import play.mvc.results.RenderJson;

import java.util.List;

public class ApiManager extends LogManager {

    protected static void apiNoContent(){
        throw new NoContent();
    }

    protected static void apiContentCreated(Object object) {
        response.status = 201;
        throw new RenderJson(object, CountrySerializer.getInstance(), CitySerializer.getInstance(), AddressSerializer.getInstance(), UserSerializer.getInstance(), OperationSerializer.getInstance(), OperationTypeSerializer.getInstance());
    }

    protected static void apiNotFound(){
        throw new NotFound();
    }

    protected static void apiNotFoundIfNull(Object object) {
        if(object == null) {
            apiNotFound();
        }
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
