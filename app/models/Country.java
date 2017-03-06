package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import java.util.UUID;

@Entity(name = "countries")
public class Country extends Model {

    public String uniq;

    @Required
    public String name;

    public String iso;

    public Country() {
        uniq = UUID.randomUUID().toString();
    }
}
