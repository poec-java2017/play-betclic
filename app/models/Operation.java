package models;

import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity(name = "operation")
public class Operation extends Model {

    public String uniq;

    @Required
    @Min(0.01)
    public BigDecimal amount;

    @Required
    public Date date;

    @ManyToOne
    @Required
    public OperationType type;

    @Required
    @ManyToOne
    public User user;

    public Operation() { uniq = UUID.randomUUID().toString(); }

    public Operation(BigDecimal amount, Date date, OperationType type, User user) {
        uniq = UUID.randomUUID().toString();
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.user = user;
    }

}
