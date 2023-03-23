
package com.ride.serviceImpl;

import com.ride.service.IDriverService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TestConfiguration {

    @Bean
    public IDriverService driverService() {
        return Mockito.mock(IDriverService.class);
    }
}

