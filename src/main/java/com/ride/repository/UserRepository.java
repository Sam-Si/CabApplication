package com.ride.repository;

import com.ride.entity.DriverProfile;
import com.ride.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long> {
    UserDetails findByToken(String authToken);

    @Modifying
    @Query("update UserDetails set password=:randomPwd where id=:id")
    int updatePasswordById(int id, String randomPwd);


    @Query("from UserDetails where emailId=:email and password=:encryptedPassword")
    UserDetails findByEmailAndPassword(String email, String encryptedPassword);

    @Modifying
    @Query("update UserDetails set token=:token where id=:id")
    void updateTokenById(int id, String token);

    @Query("from UserDetails where id=:id")
    UserDetails getById(int id);
}
