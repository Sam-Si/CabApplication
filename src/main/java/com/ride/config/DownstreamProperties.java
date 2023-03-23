package com.ride.config;


import lombok.Data;

@Data
public class DownstreamProperties {
    private String baseUrl;
    private String healthUrl;
    private Integer socketTimeout;
    private Integer connectionTimeout;
    private Integer maxTotalConnections;
    private Integer validateAfterInactivity;

    @Override
    public String toString() {
        return "DownstreamProperties{" +
                "baseUrl='" + baseUrl + '\'' +
                ", healthUrl='" + healthUrl + '\'' +
                ", socketTimeout=" + socketTimeout +
                ", connectionTimeout=" + connectionTimeout +
                ", maxTotalConnections=" + maxTotalConnections +
                ", validateAfterInactivity=" + validateAfterInactivity +
                '}';
    }
}
