package models;

import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.UUID;

@Entity(name = "event")
public class Event extends Model {

    public String uniq;
    @Required
    public Date date;
    @Required
    @Min(0.01)
    public Float coteHost;
    @Required
    @Min(0.01)
    public Float coteTie;
    @Required
    @Min(0.01)
    public Float coteGuest;
    public Integer resultHost;
    public Integer resultGuest;

    @ManyToOne
    @Required
    public Competition competition;

    @ManyToOne
    @Required
    public Team teamHost;

    @ManyToOne
    @Required
    public Team teamGuest;

    public Event() {
        uniq = UUID.randomUUID().toString();
    }

    public Event(Date date, Float coteHost, Float coteTie, Float coteGuest, int resultHost,
                 int resultGuest, Competition competition, Team teamHost, Team teamGuest) {
        uniq = UUID.randomUUID().toString();
        this.date = date;
        this.coteHost = coteHost;
        this.coteTie = coteTie;
        this.coteGuest = coteGuest;
        this.resultHost = resultHost;
        this.resultGuest = resultGuest;
        this.competition = competition;
        this.teamHost = teamHost;
        this.teamGuest = teamGuest;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
