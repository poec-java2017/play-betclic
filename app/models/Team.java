package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import java.util.UUID;

/**
 * Created by formation04 on 03/03/17.
 */
@Entity(name = "team")
public class Team extends Model {

    public String uniq;
    @Required
    public String name;
    public boolean active = true;

    public Team() {
        uniq = UUID.randomUUID().toString();
    }

    public Team(String name) {
        uniq = UUID.randomUUID().toString();
        this.name = name;
    }

}
