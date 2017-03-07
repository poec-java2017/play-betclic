package controllers;

import models.*;
import play.data.validation.Min;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.mvc.Controller;
import play.mvc.With;
import services.UserService;

import java.util.Date;
import java.util.List;

@With(SecureManager.class)
public class BetManager extends LogManager {

    public static void eventsToBet() {
        List<Event> events = Event.find("resultHost is null").fetch();
//        List<Event> events = Event.find@With(SecureManager.class)("date > now()").fetch();
        render(events);
    }

    public static void bet(@Required String idEvent,
                           @Required Short betChoice,
                           @Required @Min(0.01) Float betAmount,
                           @Required Float betQuotation) {
        // TODO Affiner la validation du betChoice

        User user = getConnectedUser();
        // L'opération suivante permet de tester avant d'avoir le formulaire de créditation du compte, wesh
        // TODO Virer cette opération
        OperationType operationType1 = new OperationType("BET");
        Operation operation = new Operation((float) 50, new Date(), operationType1, user);
        operationType1.save();
        operation.save();
        // TODO fin

        Float solde = UserService.account(user.id);
        if ((betAmount != null) && (betAmount > solde)){
            Validation.addError("amount", "Votre solde n'est pas suffisant.");
        }

        if(Validation.hasErrors()){
            params.flash();
            Validation.keep();
            EventManager.events();
        }

        //Logger.info("Solde suffisant");
        Date actionDate = new Date();
        OperationType operationType = new OperationType("BET");
        Operation betOperation = new Operation(betAmount * -1, actionDate, operationType, user);
        Event event = Event.find("uniq = ?1", idEvent).first();
        notFoundIfNull(event);
        Bet bet = new Bet(actionDate, betQuotation, betChoice, user, event, betOperation);
        operationType.save();
        betOperation.save();
        bet.save();
        EventManager.events();
    }

}
