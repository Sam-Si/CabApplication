package com.ride.service;

import com.ride.entity.UserDetails;

public interface IUserService {
    public void saveUser(UserDetails userDetails);
    UserDetails findByAuthToken(String authToken);

    int updatePassword(int id, String randomPwd);

    UserDetails findByEmailAndPassword(String emailId, String encryptedPassword);

    void updateToken(int id, String token);

    UserDetails getById(int userId);
}
