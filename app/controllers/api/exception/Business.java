package controllers.api.exception;

import play.mvc.Http;
import play.mvc.results.Result;

/**
 * Created by xylphid on 01/03/17.
 */
public class Business extends JsonResult {

    public Business(String message) {
        super(message);
    }

    @Override
    public void apply(Http.Request request, Http.Response response) {
        response.status = 409; // 409 - Conflict
        renderJson(request, response);
    }
}
