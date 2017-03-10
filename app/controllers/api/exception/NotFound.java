package controllers.api.exception;

import play.mvc.Http;
import play.mvc.results.Result;

/**
 * Created by xylphid on 01/03/17.
 */
public class NotFound extends JsonResult {

    public NotFound(String message) {
        super(message);
    }

    @Override
    public void apply(Http.Request request, Http.Response response) {
        response.status = 404; // 404 - Not Found
        renderJson(request, response);
    }
}
