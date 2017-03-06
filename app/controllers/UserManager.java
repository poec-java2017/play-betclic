package controllers;

import models.User;
import play.Logger;
import play.data.validation.Validation;
import play.mvc.With;
import services.UserService;

import javax.validation.Valid;
import java.util.List;

@With(SecureManager.class)
public class UserManager extends LogManager {

    private static final String PREFIX = "User |";

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
