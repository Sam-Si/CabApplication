package com.ride.repository;


import com.ride.entity.DriverProfile;
import com.ride.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DriverRepository extends JpaRepository<DriverProfile, Long> {


    @Modifying
    @Query("update DriverProfile set status=:status where id=:id")
    void updateStatus(int id, String status);

    @Modifying
    @Query("update DriverProfile set isOnboarded=:isOnboarded where id=:id")
    void updateOnboardedStatus(int id, boolean isOnboarded);

    @Query("from DriverProfile where userId=:userId")
    DriverProfile findByUserId(int userId);

    DriverProfile getById(Integer id);
}
