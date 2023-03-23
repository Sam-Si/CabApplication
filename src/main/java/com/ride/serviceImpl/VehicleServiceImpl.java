package com.ride.serviceImpl;

import com.ride.entity.VehicleDetails;
import com.ride.repository.VehicleRepository;
import com.ride.service.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleServiceImpl implements IVehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;
    @Override
    public void saveVehicle(VehicleDetails vehicleDetails) {
        vehicleRepository.save(vehicleDetails);
    }
}
