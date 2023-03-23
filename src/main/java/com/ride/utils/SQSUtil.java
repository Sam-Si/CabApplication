package com.ride.utils;

import com.ride.entity.DriverProfile;
import com.ride.entity.UserDetails;
import com.ride.service.IDriverService;
import com.ride.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SQSUtil {

    @Autowired
    @Lazy
    private IDriverService driverService;

    @Autowired
    @Lazy
    private IUserService userService;

    /**
     * 1.) Step-1 Notify to onbaording service onboard Driver process
     * 2.) Step-2 Send mail to user with password- user can update his password by api and will be updated in Db as well
     */

    public void doAllBackgroundTasks(DriverProfile driverProfile) {
        log.info("Driver {} on-boarding process started ",driverProfile.getId());
        driverService.onboardDriver(driverProfile.getId(),true);
        UserDetails userDetails = userService.getById(driverProfile.getUserId());
        userService.updatePassword(driverProfile.getUserId(),userDetails.getName());
    }
}
