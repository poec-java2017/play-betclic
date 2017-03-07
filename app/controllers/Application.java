package controllers;

import models.Event;

import java.util.List;

public class Application extends LogManager{

    public static void index() {
        String href = "#openBetModal";
        if(!controllers.SecureManager.Security.isConnected()){
            href = "#openErrorBetModal";
        }
        List<Event> events = Event.find("resultHost is null").fetch(10);
        render(events, href);
    }
}