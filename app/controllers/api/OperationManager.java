package controllers.api;

import controllers.api.exception.BadCredentials;
import controllers.api.exception.Business;
import controllers.api.exception.NoContent;
import controllers.service.OperationTypeService;
import models.Operation;
import models.User;
import play.Logger;
import play.data.validation.Min;
import play.data.validation.Required;
import play.data.validation.Validation;
import services.OperationService;
import services.UserService;

import java.math.BigDecimal;

public class OperationManager extends ApiSecureManager {

    public static final String PREFIX = "api.OperationManager";

    /**
     * Refill user account
     * @param userToken User uniq id
     * @param amount Refill amount
     */
    public static void refill(@Required @Min(0.01) BigDecimal amount) {
        if (Validation.hasErrors()) {
            apiBadInput(Validation.errors());
        }
        amount = amount.abs();

        // Get authenticated user
        User user = (User)request.args.get("authUser");

        // Create operation (cascade to operationType if needed)
        Operation operation = OperationService.createOperation(user, "operation.type.refill", amount);

        apiContentCreated(operation);
    }

    /**
     * Execute bet operation
     * @param userToken User uniq id
     * @param amount Bet amount
     */
    public static void bet(@Required String userToken, @Required @Min(0.01) BigDecimal amount) {
        if (Validation.hasErrors()) {
            apiBadInput(Validation.errors());
        }
        amount = amount.abs();

        // Get authenticated user
        User user = (User)request.args.get("authUser");

        if (amount.compareTo(UserService.getAccountBalance(user.id)) > 0) {
            Logger.info("[%s][bet] Tried to bet [%s] but not enougth credits.", PREFIX, amount);
            throw new Business("Not enougth credits.");
        }
        // Create operation (cascade to operationType if needed)
        Operation operation = OperationService.createOperation(user, "operation.type.bet", amount.negate());

        apiContentCreated(operation);
    }

    /**
     * Withdraw to user bank getAccountBalance
     * @param userToken User uniq id
     * @param amount Withdrawal amount
     */
    public static void withdraw(@Required String userToken, @Required @Min(0.01) BigDecimal amount) {
        if (Validation.hasErrors()) {
            apiBadInput(Validation.errors());
        }
        amount = amount.abs();

        // Get authenticated user
        User user = (User)request.args.get("authUser");

        // Compare getAccountBalance balance to withdrawal amount
        if (amount.compareTo(UserService.getAccountBalance(user.id)) > 0) {
            Logger.info("[%s][withdraw] Tried to withdraw [%s] but not enougth credits.", PREFIX, amount);
            throw new Business("Not enougth credits.");
        }
        // Create operation (cascade to operationType if needed)
        Operation operation = OperationService.createOperation(user, "operation.type.withdraw", amount.negate());

        apiContentCreated(operation);
    }

}
