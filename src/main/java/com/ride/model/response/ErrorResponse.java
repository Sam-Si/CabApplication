package com.ride.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    String errorCode;
    String errorMessage;
}
