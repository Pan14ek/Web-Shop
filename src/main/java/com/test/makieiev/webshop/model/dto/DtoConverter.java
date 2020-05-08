package com.test.makieiev.webshop.model.dto;

import com.test.makieiev.webshop.model.user.Address;
import com.test.makieiev.webshop.model.user.Role;
import com.test.makieiev.webshop.model.user.User;

public class DtoConverter {

    public User getUser(SignUpRegistrationUserFormDto signUpRegistrationUserFormDto) {
        return new User(signUpRegistrationUserFormDto.getFirstName(),
                signUpRegistrationUserFormDto.getLastName(),
                signUpRegistrationUserFormDto.getLogin(),
                signUpRegistrationUserFormDto.getEmail(),
                signUpRegistrationUserFormDto.getPassword(),
                signUpRegistrationUserFormDto.isReceivingNewsletter(),
                signUpRegistrationUserFormDto.getImageDto().getLink()
                , new Role(1, "RegisteredUser"));
    }

    public Address getAddress(AddressDto addressDto) {
        return new Address(addressDto.getCountry(),
                addressDto.getCity(),
                addressDto.getStreet(),
                addressDto.getFloor(),
                addressDto.getPost(),
                addressDto.getHouseNumber());
    }

}