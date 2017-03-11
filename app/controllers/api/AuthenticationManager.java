package controllers.api;

import com.google.gson.JsonObject;
import models.User;
import org.mindrot.jbcrypt.BCrypt;
import play.Logger;
import play.data.validation.Email;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.libs.Codec;
import play.mvc.results.RenderJson;

import java.util.Date;

public class AuthenticationManager extends ApiSecureManager {

    public static final String PREFIX = "AuthenticationManager";

    /**
     * Authenticate registered user
     * @param email User email
     * @param password user password
     */
    public static void authenticate(@Required @Email String email, @Required String password) {
        if (Validation.hasErrors()) {
            apiBadInput(Validation.errors());
        }

        User user = User.find("email = ?1", email).first();
        if (user == null || !BCrypt.checkpw(password, user.password)) {
            apiBusinessError("Wrong credentials.");
        }

        // Update last connection date
        user.lastConnection = new Date();
        user.save();

        JsonObject json = new JsonObject();
        json.addProperty("status", 202);
        json.addProperty("token", Codec.hexSHA1(user.uniq + user.lastConnection));
        // Check token with
        // token.equals(Codec.hexSHA1(user.uniq + user.lastConnection))

        renderJSON(json);
    }
}
