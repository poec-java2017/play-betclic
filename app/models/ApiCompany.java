package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import java.util.UUID;

@Entity(name = "api_companies")
public class ApiCompany extends Model {

    public String uniq;

    @Required
    public String name;

    public ApiCompany() {
        this.uniq = UUID.randomUUID().toString();
    }

    public ApiCompany(String name) {
        this.uniq = UUID.randomUUID().toString();
        this.name = name;
    }
}
