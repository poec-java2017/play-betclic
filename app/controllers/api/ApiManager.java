package controllers.api;

import controllers.LogManager;
import controllers.UserManager;
import controllers.api.exception.BadInput;
import controllers.api.exception.Business;
import controllers.api.exception.NoContent;
import controllers.api.exception.NotFound;
import controllers.api.serializer.AddressSerializer;
import controllers.api.serializer.CitySerializer;
import controllers.api.serializer.CountrySerializer;
import controllers.api.serializer.UserSerializer;
import models.Country;
import play.data.validation.Error;
import play.mvc.results.RenderJson;

import java.util.List;

public class ApiManager extends LogManager {

    protected static void apiNoContent(){
        throw new NoContent();
    }

    protected static void apiContentCreated(Object object) {
        response.status = 201;
        throw new RenderJson(object, CountrySerializer.getInstance(), CitySerializer.getInstance(), AddressSerializer.getInstance(), UserSerializer.getInstance());
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
        throw new BadInput(errors);
    }

    protected static void apiBusinessError(String s) {
        throw new Business(s);
    }
}
