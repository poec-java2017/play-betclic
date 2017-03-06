package controllers;

import models.Address;
import models.City;
import models.Country;
import models.User;
import net.sf.ehcache.search.aggregator.Count;
import org.joda.time.DateTime;
import play.Logger;
import play.data.validation.Error;
import play.data.validation.Validation;
import services.AddressService;
import services.CityService;
import services.CountryService;
import services.UserService;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserManager extends LogManager {

    private static final String PREFIX = "User |";



    public static void register() {
        List<Country> countries = Country.find("order by name").fetch();
        render(countries);
    }


    public static void signUp(@Valid User user, int day, int month, int year, String roadNumber, String streetAddress,
                              @Valid Address address, @Valid City city, @Valid Country country) {
        country = CountryService.getCountryByName(country.name);
        user.birthday = new DateTime(year, month, day, 0, 0).toDate();
        address.street = String.format("%s %s", roadNumber, streetAddress).trim();
        Logger.info("Enregistremen date : %s / %s / %s :: %s ", day, month, year, user.birthday);
        Logger.info("%s register ---> lastName=[%s] | firstName=[%s] | email=[%s] | password=[%s] | birthday=[%s] | phone=[%s]", PREFIX, user.lastName, user.firstName, user.email, user.password, user.birthday, user.phone);
        Logger.info("%s register ---> street=[%s] | postCode=[%s]", PREFIX, address.street, address.postCode);
        Logger.info("%s register ---> city=[%s]", PREFIX, city.name);
        Logger.info("%s register ---> country=[%s]", PREFIX, country.name);

        //Check des erreurs
        if (Validation.hasErrors()) {
            //Message d'erreur
            for (Error error : Validation.errors()) {
                Logger.info("[%s][%s]", error.getKey(), error.message());
            }
        }

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
            address=addressExisting;
        }

        User userExisting = UserService.getUserByMail(user.email);
        if (userExisting == null) {
            user.address = address;
            user.save();
            Logger.info("%s register ---> Validation OK", PREFIX);
        } else {
            //Message d'erreur
            Logger.info("%s register ---> User %s already exist", PREFIX, user.email);
        }

        Application.index();
    }


    public static void delete(String uniq) {
        UserService.getUserById(uniq).delete();
    }

    public static void user(String uniq) {
        render(UserService.getUserById(uniq));
    }

    public static void users() {
        List<User> users = User.findAll();
        render(users);
    }
}
