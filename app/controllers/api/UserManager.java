package controllers.api;

import controllers.service.CityService;
import controllers.service.UserService;
import models.Address;
import models.City;
import models.Country;
import models.User;
import play.Logger;
import play.data.validation.Min;
import play.data.validation.Validation;

import javax.validation.Valid;

public class UserManager extends ApiManager {
    public static final String PREFIX = "api.UserManager";

    public static void signIn(@Valid User user,@Valid Address address, @Valid City city, @Valid Country country){
        if (Validation.hasErrors()) {
            apiBadInput(Validation.errors());
        }

        //Check city :
        City cityExisting = CityService.getCityByName(city.name, country.id);
        if (cityExisting == null) {
            city.country = country;
            city.save();
            Logger.info("City register ---> Creation city : [%s]", city.name);
        } else {
            Logger.info("City register ---> City : [%s] already exist", city.name);
            city = cityExisting;
        }

        //Check address :

        //Check email :
        User userExisting = UserService.getUserByMail(user.email);
        if (userExisting == null) {
            user.address = address;
            user.save();
            Logger.info("%s register ---> Sign up OK", PREFIX);
            
            apiContentCreated(user);
        } else {
            Logger.info("%s register ---> User %s already exist", PREFIX, user.email);
        }
    }
}
