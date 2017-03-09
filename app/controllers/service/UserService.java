package controllers.service;

import models.User;

public class UserService {

    public static User getUserByMail(String email){
        User user = null;
        if(email != null) {
            user = User.find("email = ?1", email).first();
        }
        return(user);
    }
}
