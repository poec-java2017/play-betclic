package controllers.api;

import controllers.api.exception.BadCredentials;
import models.Bet;
import models.Event;
import models.User;
import org.hibernate.validator.constraints.Email;
import play.Logger;
import play.data.validation.Min;
import play.data.validation.Required;
import play.data.validation.Validation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by formation2 on 16/03/17.
 */
public class EventManager extends ApiSecureManager {

    public static final String PREFIX = "api.EventManager";

//    public static void bet(@Required String userToken,
//                           @Required String idEvent,
//                           @Required int nbEvent) {
//        if (Validation.hasErrors()) {
//            apiBadInput(Validation.errors());
//        }
//
//        // Check user
//        User user = User.find("uniq = ?1", userToken).first();
//        if (user == null) {
//            Logger.info("[%s][bet] Bad credentials with token [%s]", PREFIX, userToken);
//            throw new BadCredentials();
//        }
//
//        // Check event
//        Date date = new Date();
//        Event event = Event.find("uniq = ?1", idEvent).first();
//        apiNotFoundIfNull(event);
//
//
//        List<Event> events = Event.find("id = ?1", idEvent).fetch(nbEvent-1);
//        render(events);
//    }


    public static void bet(@Required String userToken,
                           @Required Long idEvent,
                           @Required @Min(1) int nbEvent) {
        Logger.info("[%s][bet] before basic validation with email[%s]", PREFIX, userToken);
        if (Validation.hasErrors()) {
            apiBadInput(Validation.errors());
        }
        Logger.info("[%s][bet] before email verification with email[%s]", PREFIX, userToken);
        // Check user
        // Get authenticated user
        User user = (User)request.args.get("authUser");
        if (user == null) {
            Logger.info("[%s][bet] Bad credentials with email[%s]", PREFIX, user.id);
            throw new BadCredentials();
        }
        Logger.info("[%s][bet] before bets display with idEvent [%s]", PREFIX, idEvent);

        // Render all the bets on the event attached to a user
        List<Bet> userBets = Bet.find("event_id = ?1 and user_id = ?2", idEvent, user.id).fetch();
        while (userBets.size() > nbEvent) {
            userBets.remove(0);
        }
        if (userBets.size() == nbEvent) {
            renderJSON(userBets);
        }

        //Render all the bets on the event attached to other users
        List<Bet> bets = Bet.find("event_id = ?1 and not user_id = ?2", idEvent, user.id).fetch(nbEvent);
        while (bets.size() + userBets.size() > nbEvent) {
            bets.remove(0);
        }
        userBets.addAll(bets);

        renderJSON(userBets);
    }

}
