package controllers;

import models.User;
import play.Logger;
import play.data.validation.Validation;
import services.UserService;

import javax.validation.Valid;
import java.util.List;

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

    public static void users(){
        List<User> users = User.findAll();
        render(users);
    }
}
