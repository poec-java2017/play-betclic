package services;

import models.User;
import play.Logger;
import play.db.jpa.JPA;

import javax.persistence.Query;
import java.math.BigDecimal;

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

    public static BigDecimal account(Long id) {
       Query calculAccount = JPA.em().createQuery("select SUM(amount) from operation where user_id = :id");
       try{
           BigDecimal result = (BigDecimal)calculAccount.setParameter("id", id).getSingleResult();
           //Logger.info("result = " + String.valueOf(result));
           return result == null ? BigDecimal.valueOf(0) : result;
       }catch (Exception e){
           Logger.info(e.getMessage());
           return BigDecimal.valueOf(0);
       }
    }

    public static boolean verifPassword(String pwd1, String pwd2){
        boolean check=false;
        if(pwd1.equals(pwd2)){
            check=true;
        }
        return check;
    }

}
