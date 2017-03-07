package services;

import models.City;

import java.util.List;

public class CityService {

    public static City getCityByName(String name, Long countryId){
        City city = null;
        if(name != null) {
            city = City.find("name = ?1 and country_id = ?2", name, countryId).first();
        }

        return(city);
    }
}
