package services;

import models.Operation;
import models.User;
import play.Logger;
import play.db.jpa.JPA;

import javax.persistence.Query;
import java.util.List;
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
            user = User.findById(email);
        }
        return(user);
    }

   /*public static String getPassword(String email){
        User user = getUserByMail(email);
        return(user.password);
    }*/

   public static Float account(Long id) {
       Query calculAccount = JPA.em().createQuery("select SUM(amount) from operation where user_id = :id");
       try{
           Number result = (Number) calculAccount.setParameter("id", id).getSingleResult();
           //Logger.info("result = " + String.valueOf(result));
           return result == null ? 0F : result.floatValue();
       }catch (Exception e){
           Logger.info(e.getMessage());
           return 0F;
       }
   }
}
