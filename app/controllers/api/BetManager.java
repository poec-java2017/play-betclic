package controllers.api;

import controllers.EventManager;
import controllers.api.exception.BadCredentials;
import controllers.api.exception.Business;
import services.OperationTypeService;
import models.*;
import play.Logger;
import play.data.validation.Min;
import play.data.validation.Required;
import play.data.validation.Validation;
import services.UserService;

import java.math.BigDecimal;
import java.util.Date;

public class BetManager extends ApiManager {

    public static final String PREFIX = "api.BetManager";

    public static void bet(@Required String userToken,
                           @Required @Min(0.01) BigDecimal amount,
                           @Required String idEvent,
                           @Required Short betChoice) {
        if (Validation.hasErrors()) {
            apiBadInput(Validation.errors());
        }

        // Check user
        User user = User.find("uniq = ?1", userToken).first();
        if (user == null) {
            Logger.info("[%s][bet] Bad credentials with token [%s]", PREFIX, userToken);
            throw new BadCredentials();
        }
        if (amount.compareTo(UserService.getAccountBalance(user.id)) > 0) {
            Logger.info("[%s][bet] Tried to bet [%s] but not enougth credits.", PREFIX, amount);
            throw new Business("Not enougth credits.");
        }

        // Get operation.type.bet operation type
        OperationType type = OperationTypeService.findOrCreate("operation.type.bet");

        // Check event
        Date date = new Date();
        Event event = Event.find("uniq = ?1", idEvent).first();
        apiNotFoundIfNull(event);
        Float quotation = null;
        switch(betChoice) {
            case 1:
                quotation = event.coteHost;
                break;
            case 2:
                quotation = event.coteTie;
                break;
            case 3:
                quotation = event.coteGuest;
                break;
            default:
                apiBusinessError("Choice must be 1, 2 or 3. [" + betChoice + "] given.");
        }

        // Create operation
        Operation operation = new Operation();
        operation.user = user;
        operation.operationType = type;
        operation.date = new Date();
        operation.amount = amount.negate();
        operation.save();

        Bet bet = new Bet(date, quotation, betChoice, user, event, operation);
        bet.save();

        apiContentCreated(bet);
    }

}
