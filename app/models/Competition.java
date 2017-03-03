package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by formation04 on 03/03/17.
 */
@Entity(name = "competition")
public class Competition extends Model {

    public String uniq;
    @Required
    public String name;
    public boolean active = true;

    @ManyToMany
    public List<Team> teams = new LinkedList<Team>();

    public Competition() {
        uniq = UUID.randomUUID().toString();
    }

    public Competition(String name) {
        uniq = UUID.randomUUID().toString();
        this.name = name;
    }

}
