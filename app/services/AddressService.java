package services;

import models.Address;
import play.Logger;

public class AddressService {

    public static Address getAddress(String street, String postCode){
        Address address = null;
        if(street != null && postCode != null) {
            address = Address.find("street = ?1 AND postCode = ?2", street, postCode).first();
        }
        return(address);
    }

    public static void deleteAddress(Address address){
        address.delete();
    }
}
