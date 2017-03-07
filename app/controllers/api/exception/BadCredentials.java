package controllers.api.exception;

import play.mvc.Http;
import play.mvc.results.Result;

/**
 * Created by xylphid on 01/03/17.
 */
public class BadCredentials extends Result {

    public BadCredentials() {
        super("Bad credentials");
    }

    @Override
    public void apply(Http.Request request, Http.Response response) {
        response.status = 401; // 401 - Unauthorized
        try {
            String encoding = getEncoding();
            setContentTypeIfNotSet(response, "application/json, charset=" + encoding);
            response.out.write("{\"code\":401, \"message\":\"Wrong credentials\"}".getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
