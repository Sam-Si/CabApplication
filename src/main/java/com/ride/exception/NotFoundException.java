package com.ride.exception;

import lombok.Data;

@Data
public class NotFoundException extends Exception{
    String errorCode;
    public NotFoundException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;

    }
}
