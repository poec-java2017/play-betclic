package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import java.util.UUID;

/**
 * Created by formation04 on 03/03/17.
 */
@Entity(name = "operation_type")
public class OperationType extends Model {

    static private OperationType credit, withdraw, bet, gain;

    public String uniq;
    @Required
    public String name;
//    CREDIT, WITHDRAW, BET, GAIN;

    public OperationType() { uniq = UUID.randomUUID().toString(); }

    public OperationType(String name) {
        uniq = UUID.randomUUID().toString();
        this.name = name;
    }

    static  public OperationType getCredit(){
        if (credit == null){
            credit = OperationType.find("name = CREDIT").first();
        }
        return credit;
    }

    static  public OperationType getWithdraw(){
        if (withdraw == null){
            withdraw = OperationType.find("name = WITHDRAW").first();
        }
        return credit;
    }

    static  public OperationType getBet(){
        if (bet == null){
            bet = OperationType.find("name = BET").first();
        }
        return credit;
    }

    static  public OperationType getGain(){
        if (gain == null){
            gain = OperationType.find("name = GAIN").first();
        }
        return credit;
    }

}
