package services;

import models.Country;

public class CountryService {

    public static Country getCountryByName(String name){
        Country country = null;
        if(name != null) {
            country = Country.find("name = ?1", name).first();
        }
        return(country);
    }


}
