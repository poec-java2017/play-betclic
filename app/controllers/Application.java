package controllers;

import jobs.BootstrapJob;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
        render();
    }
}