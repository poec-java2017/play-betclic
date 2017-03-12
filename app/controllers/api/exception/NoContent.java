package controllers.api.exception;

import play.mvc.Http;
import play.mvc.results.Result;

/**
 * Created by xylphid on 01/03/17.
 */
public class NoContent extends Result {

    public NoContent() {
        super("No Content");
    }

    @Override
    public void apply(Http.Request request, Http.Response response) {
        response.status = 204; // 204 - No Content
    }

}
