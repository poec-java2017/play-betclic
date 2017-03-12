package controllers.api.exception;

import play.mvc.Http;

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
