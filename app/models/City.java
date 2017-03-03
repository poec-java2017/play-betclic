package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity(name = "cities")
public class City extends Model {

    public String uniq;

    @Required
    public String name;

    @Required
    @ManyToOne
    public Country country;

    public City() {
        uniq = UUID.randomUUID().toString();
    }
}
