package controllers;

import models.Event;
import play.mvc.Controller;

import java.util.List;

public class BetManager extends Controller {

    public static void eventsToBet() {
        List<Event> events = Event.find("resultHost is null").fetch();
//        List<Event> events = Event.find("date > now()").fetch();
        renderJSON(events);
    }

}
