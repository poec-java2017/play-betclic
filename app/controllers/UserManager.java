package controllers;

import models.Country;
import models.Operation;
import models.OperationType;
import models.User;
import org.joda.time.DateTime;
import org.mindrot.jbcrypt.BCrypt;
import play.Logger;
import play.data.validation.Equals;
import play.data.validation.Error;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.mvc.With;
import services.UserService;

import javax.validation.Valid;
import java.util.List;

@With(SecureManager.class)
public class UserManager extends LogManager {

    private static final String PREFIX = "User |";

    public static void signIn(@Valid String email, String password){
        Logger.info("%s register ---> email=[%s] | password=[%s]", PREFIX, email, password);

        if(Validation.hasErrors()) {
            //Message d'erreur
            Logger.info("%s register ---> Validation errors", PREFIX);
        }

        //On vérifie si l'user existe
        User user = UserService.getUserByMail(email);

        //Si il existe on vérifie si son password est bon
        if(user!= null){
            if(password.equals(user.password)){
                //THAT'S ALL RIGHT
                Logger.info("%s register ---> Password correct", PREFIX);
            }else{
                //PASSWORD PAS BON
                Logger.info("%s register ---> Password incorrect", PREFIX);
            }
        }
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

    public static void fillIn(@Valid User user, @Required Float amount){
        Operation operation = new Operation();
        operation.amount = amount;
        operation.user = user;
        operation.date = DateTime.now().toDate();
        operation.operationType = OperationType.getCredit();
        operation.save();
    }
}
