package controllers;

import models.*;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.mvc.Controller;
import services.UserService;

import java.util.Date;
import java.util.List;

public class BetManager extends Controller {

    public static void eventsToBet() {
        List<Event> events = Event.find("resultHost is null").fetch();
//        List<Event> events = Event.find("date > now()").fetch();
        renderJSON(events);
    }

    public static void bet(@Required String idEvent,
                           @Required Short betChoice,
                           @Required Float betAmount,
                           @Required Float betQuotation) {

        // TODO : récupérer Utilisateur connecté
        User user = User.find("id = 25").first();
        OperationType operationType1 = new OperationType("BET");
        Operation operation = new Operation((float) 50, new Date(), operationType1, user);
        operationType1.save();
        operation.save();
        // TODO : pas encore fait
        Float solde = UserService.account(25L);
        if (betAmount > solde){
            //Logger.info("Solde insuffisant");
            Validation.addError("amount", "Votre solde n'est pas suffisant.");
            params.flash();
            Validation.keep();
            eventsToBet();
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
        eventsToBet();
    }

}
