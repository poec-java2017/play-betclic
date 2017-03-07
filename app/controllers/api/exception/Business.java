package controllers.api.exception;

import play.mvc.Http;
import play.mvc.results.Result;

/**
 * Created by xylphid on 01/03/17.
 */
public class Business extends Result {

    public Business(String message) {
        super(message);
    }

    @Override
    public void apply(Http.Request request, Http.Response response) {
        response.status = 409; // 409 - Conflict
        try {
            String encoding = getEncoding();
            setContentTypeIfNotSet(response, "application/json, charset=" + encoding);
            response.out.write( String.format("{\"code\":409, \"message\":\"%s\"}", this.getMessage()).getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
