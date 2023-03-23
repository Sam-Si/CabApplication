package com.ride.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ride.model.request.DriverProfileDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {

    private String token;

    private DriverProfileDto driverProfileDto;
}
