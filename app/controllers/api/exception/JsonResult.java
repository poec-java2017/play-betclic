package controllers.api.exception;

import com.google.gson.JsonObject;
import play.mvc.Http;
import play.mvc.results.Result;

public abstract class JsonResult extends Result {

    public JsonResult(String message) {
        super(message);
    }

    @Override
    public abstract void apply(Http.Request request, Http.Response response);

    public void renderJson(Http.Request request, Http.Response response) {
        try {
            // Build json response
            JsonObject json = new JsonObject();
            json.addProperty("status", response.status);
            json.addProperty("message", this.getMessage());

            // Define response
            String encoding = getEncoding();
            setContentTypeIfNotSet(response, "application/json, charset=" + encoding);
            response.out.write(json.toString().getBytes(encoding));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
