package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import java.util.UUID;

@Entity(name = "operation_type")
public class OperationType extends Model {


    public String uniq;
    @Required
    public String name;
//    CREDIT, WITHDRAW, BET, GAIN;

    public OperationType() { uniq = UUID.randomUUID().toString(); }

    public OperationType(String name) {
        uniq = UUID.randomUUID().toString();
        this.name = name;
    }

}
