package com.ride.serviceImpl;

import com.ride.entity.DriverProfile;
import com.ride.entity.VehicleDetails;
import com.ride.model.request.DriverProfileDto;
import com.ride.model.request.VehicleDetailsDto;

public class TestDummyDriver {


    public static DriverProfileDto dummyDriverProfile(){
        DriverProfileDto driverProfile = DriverProfileDto.builder()
                .status("IN_ACTIVE")
                .mobileNo("123456789")
                .emailId("test@gmail.com")
                .name("neeru")
                .userId(1234)
                .vehicleDetails(VehicleDetailsDto.builder().build())
                .isOnboarded(false)
                .build();
        return driverProfile;

    }
}
