package com.szepang.Messages;

/**
 * Created by Sze on 12/09/2017.
 */
//https://stackoverflow.com/questions/446663/best-way-to-define-error-codes-strings-in-java

public enum Error {
    TABLE_CANNOT_BE_0("Table number can not be 0 or below."),
    ZERO_NOT_ALLOWED("'0' is not a valid input, please give a positive number."),
    TABLE_FREE_TRUE("Table was already free."),
    TABLE_FREE_FALSE("Table is occupied and cannot be booked."),
    TABLE_EXIST("The table number already exists in the system."),
    NON_EXISTANT("The table specified does not exist."),
    NO_CONFIRMATION("Confirmation for request could not be processed.");


    private final String description;

    Error(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
