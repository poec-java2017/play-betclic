package services;

import models.OperationType;

/**
 * Created by formation2 on 07/03/17.
 */
public class OperationTypeService {

    static private OperationType credit, withdraw, bet, gain;

    static  public OperationType getCredit(){
        if (credit == null){
            credit = OperationType.find("name = 'CREDIT'").first();
        }
        return credit;
    }

    static  public OperationType getWithdraw(){
        if (withdraw == null){
            withdraw = OperationType.find("name = 'WITHDRAW'").first();
        }
        return credit;
    }

    static  public OperationType getBet(){
        if (bet == null){
            bet = OperationType.find("name = 'BET'").first();
        }
        return credit;
    }

    static  public OperationType getGain(){
        if (gain == null){
            gain = OperationType.find("name = 'GAIN'").first();
        }
        return credit;
    }
}
