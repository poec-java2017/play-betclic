package services;

import models.ApiClient;
import org.apache.commons.lang.RandomStringUtils;
import play.libs.Codec;

import java.util.Date;

public enum ApiService {

    INSTANCE;

    public String generateApiKey() {
        return RandomStringUtils.randomAlphanumeric(32);
    }

    public String generatePrivateApiKey() {
        return RandomStringUtils.randomAlphanumeric(128);
    }

    public static boolean validateCredentials(ApiClient apiClient, String timestamp, String control) {
        String computedControl = generateControl(apiClient.apiKey, apiClient.apiPrivateKey, timestamp);
        return computedControl.equals(control);
    }

    // TODO : Visibility to private
    public static String generateControl(String apiKey, String apiPrivateKey, String timestamp) {
        return Codec.hexSHA1(apiKey + apiPrivateKey + timestamp);
    }

    public static void main() {
        Date date = new Date();
        System.out.println(date);
        System.out.println(date.getTime()/1000);
        // Thu Mar 02 14:49:40 CET 2017
        Date lautreDate = new Date(1488462580 * 1000L);
        System.out.println(lautreDate);
        System.out.println(generateControl("BCAMqXW2s2Ez22bKY8YGvB8reJfaQJhp", "YLu4dmgKTReFUVJkYx2tnEYsvin0Uf6rJ5X7ZMX1dR6jsJr2gyUxGCbrdTQja8IDqdgxTb9R1DHuVSes5T3tIh6wmpOwkwAmwb7XWaoIiEKpIsMmsb1mErBGuZhKZl23", "1488462247"));
    }
}
