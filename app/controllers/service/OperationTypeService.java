package controllers.service;

import models.OperationType;
import play.Logger;

public class OperationTypeService {

    public static final String PREFIX = "OperationTypeService";

    public static OperationType findOrCreate(String name) {
        OperationType type = OperationType.find("name = ?1", name).first();
        if (type == null) {
            Logger.info("[%s][findOrCreate] Create new operation type [%s]", PREFIX, name);
            type = new OperationType(name);
            type.save();
        }
        return type;
    }
}
