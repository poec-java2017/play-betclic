package controllers;

import models.*;
import play.data.validation.Required;
import play.data.validation.Validation;
import play.mvc.Controller;

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
        User user = new User();
        // TODO : pas encore fait
        if (betAmount > user.account){
            Validation.addError("amount", "Votre solde n'est pas suffisant.");
            params.flash();
            Validation.keep();
            eventsToBet();
        }

        Date actionDate = new Date();
        OperationType operationType = new OperationType("BET");
        Operation betOperation = new Operation(betAmount, actionDate, operationType, user);
        Event event = Event.find("uniq = ?1", idEvent).first();
        notFoundIfNull(event);
        Bet bet = new Bet(actionDate, betQuotation, betChoice, user, event, betOperation);
        bet.save();
        eventsToBet();
    }

}
