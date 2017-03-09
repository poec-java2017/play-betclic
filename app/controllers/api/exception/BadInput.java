package controllers.api.exception;

import com.google.gson.JsonObject;
import play.data.validation.Error;
import play.mvc.Http;
import play.mvc.results.Result;

import java.util.List;

/**
 * Created by xylphid on 01/03/17.
 */
public class BadInput extends Result {
    protected List<Error> errors;

    public BadInput(List<Error> errors) {
        super("Bad input" );
        this.errors = errors;
    }

    @Override
    public void apply(Http.Request request, Http.Response response) {
        response.status = 406; // 406 - Not acceptable
        try {
            String encoding = getEncoding();
            setContentTypeIfNotSet(response, "application/json, charset=" + encoding);

            JsonObject json = new JsonObject();
            json.addProperty("code", "406");

            JsonObject errorList = new JsonObject();
            for (Error error : errors) {
                errorList.addProperty(error.getKey(), error.message());
            }
            json.add("errors", errorList);

            response.out.write(json.toString().getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
