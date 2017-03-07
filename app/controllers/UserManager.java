package controllers;

import models.Country;
import models.User;
import org.mindrot.jbcrypt.BCrypt;
import play.Logger;
import play.data.validation.Equals;
import play.data.validation.Error;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.mvc.Router;
import play.mvc.With;
import services.UserService;

import javax.validation.Valid;
import java.util.List;

@With(SecureManager.class)
public class UserManager extends LogManager {

    private static final String PREFIX = "User";

    public static void display() {
        Logger.info("[%s][display]", PREFIX);
        List<Country> countries = Country.find("order by name").fetch();

        render(countries);
    }

    public static void createOrUpdate() {
        Logger.info("[%s][createOrUpdate]", PREFIX);

        // Build URL to display form (JS required)
        String url = Router.reverse("UserManager.display").addRef("address").url;
        redirect(url);
    }

    public static void updatePassword(@Required String apppassword, @Required @Equals("apprnpassword") String appnpassword, @Required String apprnpassword) {
        Logger.info("[%s][updatePassword] [%s][%s][%s]", PREFIX, apppassword, appnpassword, apprnpassword);
        User user = getConnectedUser();
        if (Validation.hasErrors()) {
            for (Error error: Validation.errors()) {
                Logger.info("[%s][updatePassword][ValidationError] %s : %s", PREFIX, error.getKey(), error.message());
            }
            Validation.keep();
        } else if (!BCrypt.checkpw(apppassword, user.password)) {
            Logger.info("[%s][updatePassword][BusinessError] Password mismatch", PREFIX);
            flash.put("badPassword", "Password mismatch.");
        } else {
            user.password = BCrypt.hashpw(appnpassword, BCrypt.gensalt());
            user.save();
        }

        // Build URL to display form (JS required)
        String url = Router.reverse("UserManager.display").addRef("password").url;
        redirect(url);
    }

    public static void signOut(@Valid User user){
        if(Validation.hasErrors()) {
            //Message d'erreur
            Logger.info("%s save ---> Validation errors", PREFIX);
        }
        User userExisting = UserService.getUserByMail(user.email);
        if(userExisting!=null){
            user.save();
            Logger.info("%s save ---> Validation OK", PREFIX);
        }else {
            //Message d'erreur
            Logger.info("%s save ---> User %s already exist", PREFIX, user.email);
        }
    }

    public static void delete(String uniq){
        UserService.getUserById(uniq).delete();
    }

    public static void user(String uniq){
        render(UserService.getUserById(uniq));
    }

    public static void users(){
        List<User> users = User.findAll();
        render(users);
    }
}
