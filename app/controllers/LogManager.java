package controllers;

import models.User;
import play.Logger;
import play.mvc.After;
import play.mvc.Before;
import play.mvc.Controller;

public class LogManager extends Controller {

    public static final String PREFIX = "LogController";

    @Before
    private void before() {
        Logger.info("[%s][before] URL : %s", PREFIX, request.url);

        // Get user if connected
        if (session.contains("username")) {
            Logger.info("[%s][before] %s", PREFIX, session.get("username"));
            User user = User.find("email = ?1", session.get("username")).first();

            renderArgs.put("user", user);
        }
    }

    /**
     * Return connected user
     * @return User
     */
    public static User getConnectedUser() {
        return (User)renderArgs.get("user");
    }

    @After
    private void after() {
    }
}