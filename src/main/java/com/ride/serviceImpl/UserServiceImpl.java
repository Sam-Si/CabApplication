package com.ride.serviceImpl;

import com.ride.entity.UserDetails;
import com.ride.repository.UserRepository;
import com.ride.service.IUserService;
import com.ride.utils.EncryptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUser(UserDetails userDetails) {
            userRepository.save(userDetails);
    }

    @Override
    public UserDetails findByAuthToken(String authToken) {
        return userRepository.findByToken(authToken);
    }

    @Override
    public int updatePassword(int id, String randomPwd) {
        String encryptedPwd = EncryptionUtil.getEncryptedPwd(randomPwd);
        log.info("Update user password for {} and encrypted {}  ",randomPwd,encryptedPwd);
        return userRepository.updatePasswordById(id,encryptedPwd);
    }

    @Override
    public UserDetails findByEmailAndPassword(String emailId, String encryptedPassword) {
        return userRepository.findByEmailAndPassword(emailId,encryptedPassword);
    }

    @Override
    public void updateToken(int id, String token) {
        userRepository.updateTokenById(id,token);
    }

    @Override
    public UserDetails getById(int userId) {
        return userRepository.getById(userId);
    }
}
