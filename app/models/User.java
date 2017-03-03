package models;


import play.data.validation.Email;
import play.data.validation.Min;
import play.data.validation.Required;
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
    public String email;

    @Required
    @Min(8)
    public String password;

    @Required
    public Date birthday;

    public String phone;

    @ManyToOne
    public Address address;

    public User() {
        this.uniq = UUID.randomUUID().toString();
    }
}
