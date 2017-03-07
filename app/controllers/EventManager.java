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

public class EventManager extends Controller {

    public static void events() {
        if(controllers.SecureManager.Security.isConnected()){
            // Connecté : Formulaire de pari
        } else {
            // Pas connecté : messagederreurisationement
        }
        List<Event> events = Event.find("resultHost is null limit 10").fetch();
        render(events);
    }
}
