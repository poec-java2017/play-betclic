package controllers.api;

import controllers.api.exception.AlreadyExists;
import models.ApiClient;
import models.ApiCompany;
import play.Logger;
import play.data.validation.Required;
import services.ApiService;
import services.ApiCompanyService;

public class ApiClientManager extends ApiManager {

    public static final String PREFIX = "ApiClientManager";

    /**
     * Register new application client
     * @param name Client name
     * @param companyName Company name (owner)
     */
    public static void register(@Required String name, @Required String companyName) {
        Logger.info("[%s][registerClient] [%s][%s]", PREFIX, name, companyName);
        ApiCompany company = ApiCompanyService.findOrCreate(companyName);
        ApiClient apiClient = ApiClient.find("name = ?1 AND company.id = ?2", name, company.id).first();
        if (apiClient != null) {
            Logger.info("[%s][register] Api-cli [%s] from company [%s] already exists.", PREFIX, name, companyName);
            throw new AlreadyExists( "This application has already been registered.");
        }
        apiClient = new ApiClient();
        apiClient.company = company;
        apiClient.name = name;
        apiClient.apiKey = ApiService.INSTANCE.generateApiKey();
        apiClient.apiPrivateKey = ApiService.INSTANCE.generatePrivateApiKey();
        apiClient.save();

        apiContentCreated(apiClient);
    }

}
