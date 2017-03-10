package controllers.api.exception;

import com.google.gson.JsonObject;
import play.mvc.Http;
import play.mvc.results.Result;

public class AlreadyExists extends JsonResult {

    public AlreadyExists() {
        super( "This item already exists" );
    }

    public AlreadyExists(String message) {
        super(message);
    }

    @Override
    public void apply(Http.Request request, Http.Response response) {
        response.status = 409; // 409 - Conflict
        renderJson(request, response);
    }
}
