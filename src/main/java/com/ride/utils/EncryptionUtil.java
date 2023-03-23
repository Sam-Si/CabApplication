package com.ride.utils;


import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class EncryptionUtil {


    public static String getEncryptedPwd(String password){
        String hash = "35454B055CC325EA1AF2126E27707052";
        String md5Hex = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        return md5Hex;
    }
}
