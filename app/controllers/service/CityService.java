package controllers.service;

import models.City;

public class CityService {

    public static City getCityByName(String name, Long countryId){
        City city = null;
        if(name != null && countryId != null) {
            city = City.find("name = ?1 and country_id = ?2", name, countryId).first();
        }

        return(city);
    }
}
