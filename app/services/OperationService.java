package services;

import models.Operation;
import models.OperationType;
import models.User;
import play.Logger;

import java.math.BigDecimal;
import java.util.Date;

public class OperationService {

    public static final String PREFIX = "OperationService";

    public static Operation createOperation(User user, String type, BigDecimal amount) {
        try {
            // Get operation type (create if not exists yet)
            OperationType oType = OperationTypeService.findOrCreate(type);

            // Create a new operation
            Operation operation = new Operation();
            operation.user = user;
            operation.type = oType;
            operation.date = new Date();
            operation.amount = amount;
            operation.save();

            return operation;
        } catch (Exception e) {
            Logger.error( "[%s][createOperation][error] %s", PREFIX, e.getMessage() );
            return null;
        }
    }

}
