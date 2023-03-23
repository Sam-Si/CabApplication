package com.ride.controller;



import com.ride.constants.ResponseCodeMapping;
import com.ride.exception.InternalServerError;
import com.ride.exception.NotFoundException;
import com.ride.model.request.DriverProfileDto;
import com.ride.model.request.DriverStatusDto;
import com.ride.model.request.LoginRequest;
import com.ride.model.response.LoginResponse;
import com.ride.service.IDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Repository
@RequestMapping(value = "/api/v1/ride/driver")
public class DriverController {

    @Autowired
    private IDriverService driverService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity registerDriver(@Valid @RequestBody DriverProfileDto profile) throws InternalServerError {
        return ResponseEntity.ok(driverService.registerDriver(profile));
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> registerDriver(@Valid @RequestBody LoginRequest login) throws NotFoundException {
        return ResponseEntity.ok(driverService.login(login));
    }

    @RequestMapping(value = "/ready", method = RequestMethod.POST)
    public ResponseEntity markActiveStatus(@Valid @RequestBody DriverStatusDto driverStatus ) throws NotFoundException {
        driverService.markStatus(driverStatus);
        return ResponseEntity.ok(ResponseCodeMapping.SUCCESS.getMessage());
    }

    @RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
    public ResponseEntity<DriverProfileDto> markActiveStatus(@PathVariable int id) throws NotFoundException {
        return ResponseEntity.ok(driverService.getById(id));
    }

}
