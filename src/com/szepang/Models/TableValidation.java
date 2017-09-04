/*
package com.szepang.Models;

import org.springframework.validation.Validator;

*/
/**
 * Created by Sze on 27/08/2017.
 *//*

public class TableValidation implements Validator {

    private static final int MINIMUM_PASSWORD_LENGTH = 6;

    public boolean supports(Class clazz) {
        return UserLogin.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required");
        UserLogin login = (UserLogin) target;
        if (login.getPassword() != null
                && login.getPassword().trim().length() < MINIMUM_PASSWORD_LENGTH) {
            errors.rejectValue("password", "field.min.length",
                    new Object[]{Integer.valueOf(MINIMUM_PASSWORD_LENGTH)},
                    "The password must be at least [" + MINIMUM_PASSWORD_LENGTH + "] characters in length.");
        }
    }

}
*/