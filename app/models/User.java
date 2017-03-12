package models;


import play.data.validation.*;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.UUID;

@Entity(name="users")
public class User extends Model {

    public String uniq;

    @Required
    public String lastName;

    @Required
    public String firstName;

    @Required
    @Email
    @Unique
    public String email;

    @Required
    @MinSize(8)
    public String password;

    @Required
    public Date birthday;

    @Required
    @Phone
    public String phone;

    @ManyToOne
    public Address address;

    public Date lastConnection;

    // To be confirmed
    public String publicKey;

    // To be confirmed
    public String privateKey;

    public User() {
        this.uniq = UUID.randomUUID().toString();;
    }

    public static User authenticate(String email, String password) {
        return User.find("email = ?1 and password = ?2", email, password).first();
    }

}
