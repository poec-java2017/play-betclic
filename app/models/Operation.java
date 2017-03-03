package models;

import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.UUID;

/**
 * Created by formation04 on 03/03/17.
 */
@Entity(name = "operation")
public class Operation extends Model {

    public String uniq;
    @Required
    @Min(0.01)
    public Float amount;
    @Required
    public Date date;
    @ManyToOne
    @Required
    public OperationType operationType;
    @Required
    @ManyToOne
    public User user;

    public Operation() { uniq = UUID.randomUUID().toString(); }

    public Operation(Float amount, Date date, OperationType operationType, User user) {
        uniq = UUID.randomUUID().toString();
        this.amount = amount;
        this.date = date;
        this.operationType = operationType;
        this.user = user;
    }

}
