package models;


import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Entity(name="addresses")
public class Address extends Model{

    public String uniq;

    @Required
    public String street;

    @Required 
    @Pattern(regexp = "[0-9]+", message="Invalid post code")
    public String postCode;

    @ManyToOne
    public City city;

    public Address() {
        this.uniq = UUID.randomUUID().toString();
    }
}
