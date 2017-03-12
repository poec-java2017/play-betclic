package models;

import play.data.validation.Required;
import play.db.jpa.JPABase;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity(name = "api_clients")
public class ApiClient extends Model {

    public String uniq;

    @Required
    public String name;

    @Required
    public String apiKey;

    @Required
    public String apiPrivateKey;

    @ManyToOne
    public ApiCompany company;

    public ApiClient() {
        this.uniq = UUID.randomUUID().toString();
    }

}
