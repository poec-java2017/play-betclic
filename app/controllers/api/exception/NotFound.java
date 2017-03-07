package controllers.api.exception;

import play.mvc.Http;
import play.mvc.results.Result;

/**
 * Created by xylphid on 01/03/17.
 */
public class NotFound extends Result {

    public NotFound() {
        super("Not found.");
    }

    @Override
    public void apply(Http.Request request, Http.Response response) {
        response.status = 404; // 404 - Not Found
        try {
            String encoding = getEncoding();
            setContentTypeIfNotSet(response, "application/json, charset=" + encoding);
            response.out.write("{\"code\":404, \"message\":\"L'objet demandé n'existe pas.\"".getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
