package controllers;

import models.Bet;
import models.Event;
import models.Operation;
import models.User;
import play.Logger;
import play.data.validation.Min;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.mvc.With;
import services.OperationService;
import services.UserService;

import java.math.BigDecimal;
import java.util.List;

@With(SecureManager.class)
public class BetManager extends LogManager {

    public static final String PREFIX = "BetManager";

    public static void eventsToBet() {
        List<Event> events = Event.find("resultHost is null").fetch();
//        List<Event> events = Event.find@With(SecureManager.class)("date > now()").fetch();
        render(events);
    }

    public static void bet(@Required String idEvent,
                           @Required Short betChoice,
                           @Required @Min(0.01) BigDecimal betAmount,
                           @Required Float betQuotation) {
        if(Validation.hasErrors()){
            params.flash();
            Validation.keep();
            EventManager.events();
        }
        // TODO Affiner la validation du betChoice

        // Get connected user
        User user = getConnectedUser();

        // Compare getAccountBalance balance to bet amount
        if (betAmount.compareTo(UserService.getAccountBalance(user.id)) >= 0) {
            Logger.info("[%s][bet][error] Account balance is lower than bet amount", PREFIX);
            Validation.addError("amount", "Votre solde n'est pas suffisant.");
        }

        // Get event
        Event event = Event.find("uniq = ?1", idEvent).first();
        notFoundIfNull(event);

        // Create operation and bet
        Operation operation = OperationService.createOperation(user, "operation.type.bet", betAmount.negate());
        Bet bet = new Bet(operation.date, betQuotation, betChoice, user, event, operation);
        bet.save();
        // TODO : confirm payment suceeded
        // TODO : send mail

        // Redirect
        EventManager.events();
    }

}
