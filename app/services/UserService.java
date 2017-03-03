package services;

import models.User;

public class UserService {

    public static User getUserById(String uniq){
        User user = null;
        if(uniq != null) {
            user = User.findById(uniq);
        }
        return(user);
    }

    public static User getUserByMail(String email){
        User user = null;
        if(email != null) {
            user = User.find("email = ?1", email).first();
        }
        return(user);
    }

   /*public static String getPassword(String email){
        User user = getUserByMail(email);
        return(user.password);
    }*/
}
