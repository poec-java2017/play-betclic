package controllers.api.exception;

import com.google.gson.JsonObject;
import play.data.validation.Error;
import play.mvc.Http;
import play.mvc.results.Result;

public class BadParam extends Result {
    protected String error;
    public BadParam(String error) {
        super("Invalid Parametre");
        this.error = error;
    }

    @Override
    public void apply(Http.Request request, Http.Response response) {
        response.status = 406;
        try {
            String encoding = getEncoding();
            setContentTypeIfNotSet(response, "application/json, charset=" + encoding);

            JsonObject json = new JsonObject();
            json.addProperty("code", "406");


            json.addProperty("Invalid Parametre", error);

            response.out.write(json.toString().getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
