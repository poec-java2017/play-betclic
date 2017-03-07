package controllers.api;

import controllers.api.exception.BadCredentials;
import controllers.api.exception.Business;
import controllers.api.exception.NoContent;
import controllers.service.OperationTypeService;
import models.Operation;
import models.OperationType;
import models.User;
import play.Logger;
import play.data.validation.Min;
import play.data.validation.Required;
import play.data.validation.Validation;
import services.UserService;

import java.math.BigDecimal;
import java.util.Date;

public class OperationManager extends ApiManager {

    public static final String PREFIX = "api.OperationManager";

    public static void refill(@Required String userToken, @Required @Min(0.01) BigDecimal amount) {
        amount = amount.abs();
        if (Validation.hasErrors()) {
            apiBadInput(Validation.errors());
        }

        // Check user
        User user = User.find("uniq = ?1", userToken).first();
        if (user == null) {
            Logger.info("[%s][refill] Bad credentials with token [%s]", PREFIX, userToken);
            throw new BadCredentials();
        }
        // Get operation.type.refill operation type
        OperationType type = OperationTypeService.findOrCreate("operation.type.refill");

        Operation operation = new Operation();
        operation.user = user;
        operation.operationType = type;
        operation.date = new Date();
        operation.amount = amount;
        operation.save();

        apiContentCreated(operation);
    }

    public static void bet(@Required String userToken, @Required @Min(0.01) BigDecimal amount) {
        amount = amount.abs();
        if (Validation.hasErrors()) {
            apiBadInput(Validation.errors());
        }

        // Check user
        User user = User.find("uniq = ?1", userToken).first();
        if (user == null) {
            Logger.info("[%s][bet] Bad credentials with token [%s]", PREFIX, userToken);
            throw new BadCredentials();
        }
        if (amount.compareTo(UserService.account(user.id)) > 0) {
            Logger.info("[%s][bet] Tried to bet [%s] but not enougth credits.", PREFIX, amount);
            throw new Business("Not enougth credits.");
        }
        // Get operation.type.refill operation type
        OperationType type = OperationTypeService.findOrCreate("operation.type.bet");

        Operation operation = new Operation();
        operation.user = user;
        operation.operationType = type;
        operation.date = new Date();
        operation.amount = amount.negate();
        operation.save();

        apiContentCreated(operation);
    }

    public static void withdraw(@Required String userToken, @Required @Min(0.01) BigDecimal amount) {
        amount = amount.abs();
        if (Validation.hasErrors()) {
            apiBadInput(Validation.errors());
        }

        // Check user
        User user = User.find("uniq = ?1", userToken).first();
        if (user == null) {
            Logger.info("[%s][bet] Bad credentials with token [%s]", PREFIX, userToken);
            throw new BadCredentials();
        }
        if (amount.compareTo(UserService.account(user.id)) > 0) {
            Logger.info("[%s][withdraw] Tried to withdraw [%s] but not enougth credits.", PREFIX, amount);
            throw new Business("Not enougth credits.");
        }
        // Get operation.type.refill operation type
        OperationType type = OperationTypeService.findOrCreate("operation.type.withdraw");

        Operation operation = new Operation();
        operation.user = user;
        operation.operationType = type;
        operation.date = new Date();
        operation.amount = amount.negate();
        operation.save();

        apiContentCreated(operation);
    }

}
