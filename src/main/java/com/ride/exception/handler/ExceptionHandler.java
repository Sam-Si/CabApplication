package com.ride.exception.handler;

import com.ride.exception.InternalServerError;
import com.ride.exception.NotFoundException;
import com.ride.exception.TokenMissingException;
import com.ride.model.response.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(InternalServerError.class)
    protected ResponseEntity<Object> handleInternalServerException(InternalServerError ex, WebRequest webRequest) {
        ErrorResponse inclusiveException = ErrorResponse.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity(inclusiveException, new HttpHeaders(), HttpStatus.OK);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest webRequest) {
        ErrorResponse inclusiveException = ErrorResponse.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity(inclusiveException, new HttpHeaders(), HttpStatus.OK);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler(TokenMissingException.class)
    protected ResponseEntity<Object> handleServiceException(TokenMissingException ex, WebRequest webRequest) {
        ErrorResponse inclusiveException = ErrorResponse.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getMessage())
                .build();
        return new ResponseEntity(inclusiveException, new HttpHeaders(), HttpStatus.OK);
    }

}
