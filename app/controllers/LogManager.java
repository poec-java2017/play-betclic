package controllers;

import play.Logger;
import play.mvc.Before;
import play.mvc.Controller;

public class LogManager extends Controller {

    @Before
    private void before() {
        Logger.info(request.url);
}
