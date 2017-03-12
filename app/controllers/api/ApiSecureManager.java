package controllers.api;

import controllers.api.exception.BadCredentials;
import models.ApiClient;
import models.User;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import play.Logger;
import play.Play;
import play.mvc.Before;
import play.mvc.Router;
import services.ApiService;

public class ApiSecureManager extends ApiManager {

    public static final String PREFIX = "ApiSecureManager";

    @Before
    public static void before() {
        // Get apikey.enabled configuration
        boolean apiKeyEnabled = Boolean.parseBoolean(Play.configuration.getProperty("apikey.enabled", "false"));
        Logger.info("[%s][before][apiKeyEnabled] %s", PREFIX, apiKeyEnabled);
        if (apiKeyEnabled) {
            validateApiClient();
            validateUser();
        }
    }

    /**
     * Check api client
     */
    protected static void validateApiClient() {
        // Get params from request
        String apiKey = request.params._contains("apiKey") ? request.params.get("apiKey") : "";
        String timestamp = request.params._contains("ts") ? request.params.get("ts") : "";
        String control = request.params._contains("ctrl") ? request.params.get("ctrl") : "";
        Logger.info("[%s][before][control] %s", PREFIX, ApiService.generateControl("K2ZaSYczA13ppgXetAofyBFk0oJKB7o5", "POXTy7dit0WFQ5YtHMEqocsS9xagfoYbFgvFGRXWy3QY1diohZCFu4S1Th8vGbl3rtLB8874t1E5NI27QucZm3MeZS73RmCwv9dH3rS0af63DU2LOzBxxviUDEbeHjTi", timestamp));

        // Check required security params
        if (StringUtils.isBlank(apiKey)) {
            apiBusinessError("No API key.");
        }
        if (StringUtils.isBlank(timestamp)) {
            apiBusinessError("No timestamp.");
        }
        if (StringUtils.isBlank(control)) {
            apiBusinessError("No control.");
        }

        // Check
        Logger.info("[%s] Check valid timestamp", PREFIX);
        DateTime requestDate = new DateTime(Long.parseLong(timestamp) * 1000L);
        DateTime obsoleteDate = new DateTime().minusMinutes(100);
        if (requestDate.isBefore(obsoleteDate)) {
            Logger.info("[%s] Check valid timestamp : failed", PREFIX);
            apiBusinessError("Query is obsolete.");
        }

        ApiClient apiClient = ApiClient.find("apiKey = ?1", apiKey).first();
        apiNotFoundIfNull(apiClient, "Api-cli is not registered.");

        if (!ApiService.validateCredentials(apiClient, timestamp, control)) {
            apiBusinessError("Api-cli authentication failed.");
        }
        Logger.info("[%s][before] Api-cli is authenticated", PREFIX);
    }

    protected static void validateUser() {
        Logger.info("[%s][validateUser] %s : %s", PREFIX, request.url, Router.reverse("api.AuthenticationManager.authenticate").url);
        // Check user authentication if not on authenticate api
        if (!request.url.equals(Router.reverse("api.AuthenticationManager.authenticate").url)) {
            // Get params from request
            String userKey = request.params._contains("upk") ? request.params.get("upk") : "";

            // Check required security params
            if (StringUtils.isBlank(userKey)) {
                apiBadCredentials("No user authentication token");
            }

            // Search for associater user
            User user = User.find("publicKey = ?1", userKey).first();
            if (user == null) {
                Logger.info("[%s][validateUser][error] No match for publicKey [%s]", PREFIX, userKey);
                apiBusinessError("User authentication failed");
            }
            Logger.info("[%s][validateUser] Authenticated user : %s %s", PREFIX, user.firstName, user.lastName);
            request.args.put("authUser", user);
        }
    }
}
