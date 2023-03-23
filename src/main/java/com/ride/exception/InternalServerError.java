package com.ride.exception;

import lombok.Data;

@Data
public class InternalServerError extends Exception{
    String errorCode;
    public InternalServerError(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;

    }
}
