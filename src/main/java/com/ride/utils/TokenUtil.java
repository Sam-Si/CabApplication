package com.ride.utils;

import com.ride.entity.UserDetails;

import java.util.UUID;

public class TokenUtil {
    public static String generateToken() {
      //generate token with user details & with expiry
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
