package models;


import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.UUID;

@Entity(name="user")
public class User extends Model {
    public String uniq;

    public String lastName;

    public String firstName;

    public String email;

    public Date birthday;

    public String telephone;

    @ManyToOne
    public Address address;

    public User() {
        this.uniq = UUID.randomUUID().toString();
    }
}
