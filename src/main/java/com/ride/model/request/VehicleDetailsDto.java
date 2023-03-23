package com.ride.model.request;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include. NON_NULL)
public class VehicleDetailsDto {

    @NotBlank(message = "vehicle model is mandatory")
    private String vehicleModel;

    @NotBlank(message = "vehicle type is mandatory")
    private String  carType;

    @NotBlank(message = "vehicle color is mandatory")
    private String color;

    private String fuelType;

    @NotBlank(message = " vehicle member capacity is mandatory")
    private int memberCapacity;
}
