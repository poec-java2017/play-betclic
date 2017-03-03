package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Entity(name = "countries")
public class Country extends Model {

    public String uniq;

    @Required
    public String name;

    @Required
    public String iso;

    public Country() {
        uniq = UUID.randomUUID().toString();
    }
}
