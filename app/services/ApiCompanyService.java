package services;

import models.ApiCompany;
import play.Logger;
import play.data.validation.Required;

public class ApiCompanyService {

    public static final String PREFIX = ApiCompanyService.class.toString();

    public static ApiCompany findOrCreate(@Required String name) {
        ApiCompany apiCompany = ApiCompany.find("name = ?1", name).first();
        if (apiCompany == null) {
            Logger.info("[%s][findOrCreate] Create new company [%s]", PREFIX, name);
            apiCompany = new ApiCompany(name);
            apiCompany.save();
        }
        return apiCompany;
    }
}
