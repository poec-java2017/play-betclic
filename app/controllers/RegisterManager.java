package controllers;

import models.Address;
import models.City;
import models.Country;
import models.User;
import org.joda.time.DateTime;
import play.Logger;
import play.data.validation.Error;
import play.data.validation.Min;
import play.data.validation.Validation;
import services.AddressService;
import services.CityService;
import services.CountryService;
import services.UserService;

import javax.validation.Valid;
import java.util.List;

public class RegisterManager extends LogManager {

    private static final String PREFIX = "RegisterManager";
    private static final String VERIF = "Verif |";

    public static void register() {
        List<Country> countries = Country.find("order by name").fetch();
        render(countries);
    }

    public static void signUp(@Valid User user, @Min(1) int day, @Min(1) int month, @Min(1) int year, String password1,
                              @Valid Address address, @Valid City city, @Valid Country country, boolean checkBox) {

        country = CountryService.getCountryByName(country.name);
        boolean dateValid;
        if (year == 0 || month == 0 || day == 0) {
            dateValid = false;
        } else {
            user.birthday = new DateTime(year, month, day, 0, 0).toDate();
            dateValid = true;
        }

        Logger.info("Enregistrement date : %s / %s / %s :: %s ", day, month, year, user.birthday);
        Logger.info("%s register ---> lastName=[%s] | firstName=[%s] | email=[%s] | password=[%s] | birthday=[%s] | phone=[%s]", PREFIX, user.lastName, user.firstName, user.email, user.password, user.birthday, user.phone);
        Logger.info("%s register ---> street=[%s] | postCode=[%s]", PREFIX, address.street, address.postCode);
        Logger.info("%s register ---> city=[%s]", PREFIX, city.name);
        Logger.info("%s register ---> country=[%s]", PREFIX, country.name);
        Logger.info("%s register ---> chexbox=[%s]", PREFIX, checkBox);

        //Check des erreurs
        validation.valid(user);
        validation.valid(address);
        if (Validation.hasErrors()) {
            for (Error error : Validation.errors()) {
                Logger.info("[%s][%s]", error.getKey(), error.message());
            }
            params.flash();
            Validation.keep();
            register();
        }

        //Vérification de la date
        if (!dateValid) {
            params.flash();
            Validation.keep();
            flash.put("invalidDate", "");
            register();
        }

        //Vérification du checkbox
        if (!checkBox) {
            Logger.info("%s ---> Checkbox = false", VERIF);
            params.flash();
            Validation.keep();
            flash.put("checkbox", "");
            register();
        }

        //Vérification du mot de passe Identique
        Boolean checkPwd = UserService.verifPassword(password1, user.password);
        if (!checkPwd) {
            Logger.info("%s ---> password [%s] not the same [%s]", VERIF, password1, user.password);
            params.flash();
            Validation.keep();
            flash.put("passwordMismatch", "");
            register();
        }

        //Vérification du code postal
        try {
            Integer.parseInt(address.postCode);
            Logger.info("%s --->postCode [%s] valid", VERIF, address.postCode);

        } catch (NumberFormatException nfe) {
            Logger.info("%s --->postCode [%s] not valid", VERIF, address.postCode);
            params.flash();
            Validation.keep();
            flash.put("postCodeFalse", "");
            register();
        }


        //Vérification de la ville
        City cityExisting = CityService.getCityByName(city.name, country.id);
        if (cityExisting == null) {
            city.country = country;
            city.save();
            Logger.info("City register ---> Creation city : [%s]", city.name);
        } else {
            Logger.info("City register ---> City : [%s] already exist", city.name);
            city = cityExisting;
        }

        //Vérification de l'addresse
        Address addressExisting = AddressService.getAddress(address.street, address.postCode);
        if (addressExisting == null) {
            address.city = city;
            address.save();
            Logger.info("Address register ---> Creation address : [%s] [%s]", address.street, address.postCode);
        } else {
            Logger.info("Address register ---> Address : [%s] [%s] already exist", address.street, address.postCode);
            address = addressExisting;
        }

        User userExisting = UserService.getUserByMail(user.email);
        if (userExisting == null) {
            user.address = address;
            user.save();
            Logger.info("%s register ---> Validation OK", PREFIX);
        } else {
            Logger.info("%s register ---> User %s already exist", PREFIX, user.email);
            params.flash();
            Validation.keep();
            flash.put("emailExisting", "");
            register();
        }

        Application.index();
    }

}
