package controllers.api;

import controllers.service.CityService;
import controllers.service.UserService;
import models.Address;
import models.City;
import models.Country;
import models.User;
import org.joda.time.DateTime;
import play.Logger;
import play.data.validation.Min;
import play.data.validation.Validation;
import services.AddressService;
import services.CountryService;

import javax.validation.Valid;

public class UserManager extends ApiManager {
    public static final String PREFIX = "api.UserManager";

    public static void signIn(@Valid User user,@Valid Address address, @Valid City city, @Valid Country country, @Min(1) int day, @Min(1) int month, @Min(1) int year){
        //Convertion date
        try {
            user.birthday = new DateTime(year, month, day, 0, 0).toDate();
        } catch (Exception e) {
            apiBadParam("Invalid Date");
        }

        validation.valid(user);
        validation.valid(address);
        if (Validation.hasErrors()) {
            apiBadInput(Validation.errors());
        }

        //Check country :
        Country countryExisting = CountryService.getCountryByName(country.name);
        if (countryExisting==null){
            String messageError="Country "+country.name+" not exist";
            apiBadParam(messageError);
        }else {
            country = CountryService.getCountryByName(country.name);
        }

        Logger.info("Enregistrement date : %s / %s / %s :: %s ", day, month, year, user.birthday);
        Logger.info("%s register ---> lastName=[%s] | firstName=[%s] | email=[%s] | password=[%s] | birthday=[%s] | phone=[%s]", PREFIX, user.lastName, user.firstName, user.email, user.password, user.birthday, user.phone);
        Logger.info("%s register ---> street=[%s] | postCode=[%s]", PREFIX, address.street, address.postCode);
        Logger.info("%s register ---> city=[%s]", PREFIX, city.name);
        Logger.info("%s register ---> country=[%s]", PREFIX, country.name);


        //Check year (1905-1999)
        if (year > 1999 || year < 1905) {
            String messageError = "Year " + year + " need to be include between 1999 and 1905";
            apiBadParam(messageError);
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

        //Vérification du code postal
        try {
            Integer.parseInt(address.postCode);
            Logger.info("%s --->postCode [%s] valid", PREFIX, address.postCode);
        } catch (NumberFormatException nfe) {
            Logger.info("%s --->postCode [%s] not valid", PREFIX, address.postCode);
            apiBadParam("Post Code");
        }

        Address addressExisting = AddressService.getAddress(address.street, address.postCode);
        if (addressExisting == null) {
            address.city = city;
            address.save();
            Logger.info("Address register ---> Creation address : [%s] [%s]", address.street, address.postCode);
        } else {
            Logger.info("Address register ---> Address : [%s] [%s] already exist", address.street, address.postCode);
            address = addressExisting;
        }

        //Check user :

        User userExisting = UserService.getUserByMail(user.email);
        if (userExisting == null) {
            user.address = address;
            user.save();
            Logger.info("%s register ---> Sign up OK", PREFIX);
            apiContentCreated(user);
        } else {
            Logger.info("%s register ---> User %s already exist", PREFIX, user.email);
            String messageError="Mail "+user.email+" already exist";
            apiBadParam(messageError);
        }
    }
}
