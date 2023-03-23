/*
package com.ride.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Feign;
import feign.Logger;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OnBoardingFeignConfig {

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private OnboardingClientConfig onboardingClient;
    @Bean
    OnBoardingClient onboardingClient() {
        return (OnBoardingClient) Feign.builder()
                .client(createApacheHttpClient(1000 ,1000,100,20))
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .logLevel(Logger.Level.FULL)
                .target(OnboardingClientConfig.class, "");
    }
    public static ApacheHttpClient createApacheHttpClient(Integer socketTimeout, Integer connectTimeout,
                                                          int connectRequestTimeout, Integer maxConnections) {
        final RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectRequestTimeout).build();
        final PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(maxConnections);
        connManager.setDefaultMaxPerRoute(maxConnections);
        CloseableHttpClient client = HttpClients.custom()
                .setDefaultRequestConfig(defaultRequestConfig)
                .setConnectionManager(connManager)
                .build();
        return new ApacheHttpClient(client);
    }

}
*/
