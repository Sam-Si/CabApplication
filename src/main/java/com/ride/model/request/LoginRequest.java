package com.ride.model.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class LoginRequest {

    @NotBlank(message = "emailId is mandatory")
    //Email regex validation
    private String emailId;

    @NotBlank(message = "password is mandatory")
    //Size validation- min(5)- max(20)
    private String password;
}
