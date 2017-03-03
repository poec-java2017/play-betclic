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
        Logger.info("%s signIn ---> email=[%s] | password=[%s]", PREFIX, email, password);

        if(Validation.hasErrors()) {
            //Message d'erreur
            Logger.info("%s signIn ---> Validation errors", PREFIX);
        }

        //On vérifie si l'user existe
        User user = UserService.getUserByMail(email);

        //Si il existe on vérifie si son password est bon
        if(user!= null){
            if(password.equals(user.password)){
                //THAT'S ALL RIGHT
                Logger.info("%s signIn ---> Password correct", PREFIX);
            }else{
                //PASSWORD PAS BON
                Logger.info("%s signIn ---> Password incorrect", PREFIX);
            }
        }
    }
    public static void register(){
        render();
    }
    public static void signUp(@Valid User user){
        Logger.info("%s register ---> lastName=[%s] | firstName=[%s] | email=[%s] | password=[%s] | birthday=[%s] | phone=[%s]", PREFIX, user.lastName, user.firstName, user.email, user.password, user.birthday, user.phone);

        if(Validation.hasErrors()) {
            //Message d'erreur
            Logger.info("%s register ---> Validation errors", PREFIX);
        }
        User userExisting = UserService.getUserByMail(user.email);
        if(userExisting==null){
            user.save();
            Logger.info("%s register ---> Validation OK", PREFIX);
        }else {
            //Message d'erreur
            Logger.info("%s register ---> User %s already exist", PREFIX, user.email);
        }

        Application.index();
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
