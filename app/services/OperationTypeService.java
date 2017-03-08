package services;

import models.OperationType;
import play.Logger;

public class OperationTypeService {

    private static OperationType credit, withdraw, bet, gain;

    public static final String PREFIX = "OperationTypeService";

    public static OperationType findOrCreate(String name) {
        OperationType type = OperationType.find("name = ?1", name).first();
        if (type == null) {
            Logger.info("[%s][findOrCreate] Create new operation type []", PREFIX, name);
            type = new OperationType(name);
            type.save();
        }
        return type;
    }

    public static OperationType getCredit(){
        if(credit == null){
            credit = OperationType.find("name = 'CREDIT'").first();
        }
        return credit;
    }

    public static OperationType getWithdraw(){
        if(withdraw == null){
            withdraw = OperationType.find("name = 'WITHDRAW'").first();
        }
        return withdraw;
    }

    public static OperationType getBet(){
        if(bet == null){
            bet = OperationType.find("name = 'BET'").first();
        }
        return bet;
    }

    public static OperationType getGain(){
        if(gain == null){
            gain = OperationType.find("name = 'GAIN'").first();
        }
        return gain;
    }
}
