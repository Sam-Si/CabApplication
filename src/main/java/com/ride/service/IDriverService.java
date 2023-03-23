package com.ride.service;

import com.ride.entity.DriverProfile;
import com.ride.exception.InternalServerError;
import com.ride.exception.NotFoundException;
import com.ride.model.request.DriverProfileDto;
import com.ride.model.request.DriverStatusDto;
import com.ride.model.request.LoginRequest;
import com.ride.model.response.LoginResponse;

public interface IDriverService {
    DriverProfile registerDriver(DriverProfileDto profile) throws InternalServerError;

    void markStatus(DriverStatusDto driverStatus) throws NotFoundException;

    LoginResponse login(LoginRequest login) throws NotFoundException;

    public void onboardDriver(int id, boolean isOnboarded);

    DriverProfileDto getById(int id) throws NotFoundException;
}
