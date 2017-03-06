package services;

import models.Address;

public class AddressService {

    public static Address getAddress(String street, String postCode){
        Address address = null;
        if(address != null) {
            address = Address.find("name = ?1 AND postCode = ?2", street, postCode).first();
        }
        return(address);
    }
}
