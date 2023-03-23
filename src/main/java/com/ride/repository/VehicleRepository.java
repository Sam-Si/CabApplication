package com.ride.repository;

import com.ride.entity.UserDetails;
import com.ride.entity.VehicleDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VehicleRepository extends JpaRepository<VehicleDetails, Long> {
}
