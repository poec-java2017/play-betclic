package controllers.api.exception;

import com.google.gson.JsonObject;
import play.mvc.Http;
import play.mvc.results.Result;

/**
 * Created by xylphid on 01/03/17.
 */
public class BadCredentials extends JsonResult {

    public BadCredentials() {
        super("Wrong credentials");
    }

    public BadCredentials(String message) {
        super(message);
    }

    @Override
    public void apply(Http.Request request, Http.Response response) {
        response.status = 401; // 401 - Unauthorized
        renderJson(request, response);
    }
}
