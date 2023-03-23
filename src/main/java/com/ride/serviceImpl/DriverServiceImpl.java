package com.ride.serviceImpl;


import com.ride.constants.DriverStatus;
import com.ride.constants.ResponseCodeMapping;
import com.ride.entity.DriverProfile;
import com.ride.entity.UserDetails;
import com.ride.entity.VehicleDetails;
import com.ride.exception.InternalServerError;
import com.ride.exception.NotFoundException;
import com.ride.model.request.DriverProfileDto;
import com.ride.model.request.DriverStatusDto;
import com.ride.model.request.LoginRequest;
import com.ride.model.request.VehicleDetailsDto;
import com.ride.model.response.LoginResponse;
import com.ride.repository.DriverRepository;
import com.ride.service.IDriverService;
import com.ride.service.IUserService;
import com.ride.service.IVehicleService;
import com.ride.utils.EncryptionUtil;
import com.ride.utils.SQSUtil;
import com.ride.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Slf4j
@Service
public class DriverServiceImpl implements IDriverService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IVehicleService vehicleService;

    @Autowired
    private SQSUtil sqsUtil;

    @Autowired
    private DriverRepository driverRepository;


    public DriverServiceImpl(IUserService userService, IVehicleService vehicleService, SQSUtil sqsUtil, DriverRepository driverRepository) {
        this.userService = userService;
        this.vehicleService = vehicleService;
        this.sqsUtil = sqsUtil;
        this.driverRepository = driverRepository;

    }


    @Override
    @Transactional
    public DriverProfile registerDriver(DriverProfileDto profile) throws InternalServerError {
        UserDetails userDetails = UserDetails.builder()
                .emailId(profile.getEmailId())
                .mobileNo(profile.getMobileNo())
                .name(profile.getName())
                .build();

        userService.saveUser(userDetails);
        log.info("User data is saved for mobile {} ",profile.getMobileNo());
        VehicleDetails vehicleDetails = VehicleDetails.builder()
                .vehicleModel(profile.getVehicleDetails().getVehicleModel())
                .fuelType(profile.getVehicleDetails().getFuelType())
                .carType(profile.getVehicleDetails().getCarType())
                .color(profile.getVehicleDetails().getColor())
                .memberCapacity(profile.getVehicleDetails().getMemberCapacity())
                .build();

        vehicleService.saveVehicle(vehicleDetails);
        log.info("Vehicle data is saved for mobile {} ",profile.getMobileNo());
        DriverProfile driverProfile = DriverProfile.builder()
                .userId(userDetails.getId())
                .status(DriverStatus.IN_ACTIVE.name())
                .vehicleDetails(vehicleDetails)
                .created(new Date())
                .updated(new Date())
                .build();
        try{
             driverRepository.save(driverProfile);
            log.info("driver data is saved for mobile {} ",profile.getMobileNo());


            //process other tasks in async - queue/thread-pool
            sqsUtil.doAllBackgroundTasks(driverProfile);
            return driverProfile;
        } catch(Exception e){
            log.error("Exception in registerDriver ",e);
            throw new InternalServerError(ResponseCodeMapping.SOMETHING_WENT_WRONG.getCode(),ResponseCodeMapping.SOMETHING_WENT_WRONG.getMessage());
        }

    }




    public void onboardDriver(int id, boolean isOnboarded) {
        driverRepository.updateOnboardedStatus(id,isOnboarded);
    }

    @Override
    public DriverProfileDto getById(int id) throws NotFoundException {
        DriverProfile driverProfile = driverRepository.getById(id);
        if(ObjectUtils.isEmpty(driverProfile)){
            throw new NotFoundException(ResponseCodeMapping.DETAILS_NOT_FOUND.getCode(),ResponseCodeMapping.DETAILS_NOT_FOUND.getMessage());
        }
        UserDetails userDetails = userService.getById(driverProfile.getUserId());
        return DriverProfileDto.builder()
                .id(driverProfile.getId())
                .emailId(userDetails.getEmailId())
                .mobileNo(userDetails.getMobileNo())
                .isOnboarded(driverProfile.isOnboarded())
                .status(driverProfile.getStatus())
                .vehicleDetails(VehicleDetailsDto.builder()
                        .vehicleModel(driverProfile.getVehicleDetails().getVehicleModel())
                        .memberCapacity(driverProfile.getVehicleDetails().getMemberCapacity())
                        .color(driverProfile.getVehicleDetails().getColor())
                        .carType(driverProfile.getVehicleDetails().getCarType())
                        .build())
                .build();
    }


    //Allow a driver to mark when they are ready to take a ride
    @Override
    @Transactional
    public void markStatus(DriverStatusDto driverStatus) throws NotFoundException {
        if(!validateStatus(driverStatus.getStatus())){
           throw new NotFoundException(ResponseCodeMapping.NOT_FOUND.getCode(), ResponseCodeMapping.NOT_FOUND.getMessage());
        }
      DriverProfile driverProfile = driverRepository.getById(driverStatus.getId());
        if(!driverProfile.isOnboarded()){
            throw new NotFoundException(ResponseCodeMapping.ON_BOARDING_INCOMPLETE.getCode(),ResponseCodeMapping.ON_BOARDING_INCOMPLETE.getMessage());
        }
        log.info("Driver status update for id {} ,status {}  ",driverStatus.getId(),driverStatus.getStatus());
        driverRepository.updateStatus(driverStatus.getId(),driverStatus.getStatus());
    }

    private boolean validateStatus(String status) {
        return DriverStatus.validate(status);
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest login) throws NotFoundException {
        return validateUser(login);
    }

    private LoginResponse validateUser(LoginRequest login) throws NotFoundException {
        String encryptedPassword = EncryptionUtil.getEncryptedPwd(login.getPassword());
        log.info("Check User for email {} and pwd {} ",login.getEmailId(),encryptedPassword);
        UserDetails userDetails = userService.findByEmailAndPassword(login.getEmailId(),encryptedPassword);
        if(ObjectUtils.isEmpty(userDetails)){
            log.error("User not found for email {} and pwd {} ",login.getEmailId(),encryptedPassword);
            throw new NotFoundException(ResponseCodeMapping.NOT_FOUND.getCode(), ResponseCodeMapping.NOT_FOUND.getMessage());
        }
        log.info("Check driver for userid {}  ",userDetails.getId());
        DriverProfile driverProfile = driverRepository.findByUserId(userDetails.getId());
        if(ObjectUtils.isEmpty(driverProfile)){
            log.error("Driver profile not found for email {} ",login.getEmailId());
            throw new NotFoundException(ResponseCodeMapping.DETAILS_NOT_FOUND.getCode(),ResponseCodeMapping.DETAILS_NOT_FOUND.getMessage());
        }
        log.info("Login generate token for userid {}  ",userDetails.getId());
        String token = TokenUtil.generateToken();
        log.info("update token {} for userid {}  ",token.substring(0,9),userDetails.getId());
        userService.updateToken(userDetails.getId(),token.substring(0,9));
        DriverProfileDto driverProfileDto=  DriverProfileDto.builder()
                .name(userDetails.getName())
                .id(driverProfile.getId()).build();
       return LoginResponse.builder().token(token)
                .driverProfileDto(driverProfileDto).build();
    }
}
