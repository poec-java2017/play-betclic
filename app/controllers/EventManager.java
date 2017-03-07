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

public class EventManager extends LogManager {

    public static void events() {
        String href = "#openBetModal";
        if(!controllers.SecureManager.Security.isConnected()){
            href = "#openErrorBetModal";
        }
        List<Event> events = Event.find("resultHost is null").fetch(15);
        render(events, href);
    }
}
