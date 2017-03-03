package models;

import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.UUID;

@Entity(name = "bet")
public class Bet extends Model {

    public String uniq;
    @Required
    public Date date;
    @Required
    @Min(0.01)
    public Float quotation;
    @Required
    public Short choice;

    @ManyToOne
    public User user;

    @ManyToOne
    public Event event;

    @ManyToOne
    public Operation betOperation;

    @ManyToOne
    public Operation winOperation;


    public Bet() { uniq = UUID.randomUUID().toString(); }

    public Bet(Date date, Float quotation, Short choice, User user, Event event, Operation betOperation) {
        uniq = UUID.randomUUID().toString();
        this.date = date;
        this.quotation = quotation;
        this.choice = choice;
        this.user = user;
        this.event = event;
        this.betOperation = betOperation;
    }
}
