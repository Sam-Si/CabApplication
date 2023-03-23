package com.ride.model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverStatusDto  {

    @NotNull
    private Integer id;

    @NotBlank(message = "driver status is mandatory")
    private String status;  //TODO check validate enum in reqeust

}
