package services;

import models.City;

public class CityService {

    public static City getCityByName(String name){
        City city = null;
        if(name != null) {
            city = City.find("name = ?1", name).first();
        }
        return(city);
    }
}
