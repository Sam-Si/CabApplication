package com.ride.serviceImpl;

import com.ride.MainApplication;
import com.ride.entity.DriverProfile;
import com.ride.entity.UserDetails;
import com.ride.exception.InternalServerError;
import com.ride.model.request.DriverProfileDto;
import com.ride.repository.DriverRepository;
import com.ride.service.IDriverService;
import com.ride.service.IUserService;
import com.ride.service.IVehicleService;
import com.ride.utils.SQSUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class DriverServiceImplTest {
    private IUserService userService;
    private IVehicleService vehicleService;
    private SQSUtil sqsUtil;
    private DriverRepository driverRepository;
    private DriverProfileDto driver;

    @Before
    public void beforeTest() {
        driver = TestDummyDriver.dummyDriverProfile();
        // Mock IUserService, IVehicleService, SQSUtil, DriverRepository
        userService = Mockito.mock(IUserService.class);
        vehicleService = Mockito.mock(IVehicleService.class);
        sqsUtil = Mockito.mock(SQSUtil.class);
        driverRepository = Mockito.mock(DriverRepository.class);
    }

    @Test
    public void registerDriver() throws InternalServerError {
        IDriverService driverService = new DriverServiceImpl(userService, vehicleService, sqsUtil, driverRepository);
        UserDetails neeruUserDetails = UserDetails.builder()
                .name("neeru")
                .emailId("test@gmail.com")
                .build();
        // Mocks
        Mockito.when(userService.getById(any())).thenReturn(neeruUserDetails);
        // Mock user service for getById
        Mockito.when(userService.getById(any())).thenReturn(neeruUserDetails);
        // Mock user service save user, userservice.saveUser(any()) is a void method
        doNothing().when(userService).saveUser(any());
        // Driver logic
        DriverProfile driverProfile = driverService.registerDriver(driver);
        UserDetails userDetails = userService.getById(driverProfile.getUserId());
        Assert.assertEquals(neeruUserDetails.getName(), userDetails.getName());
    }
}
