package com.ride.constants;

public enum DriverStatus {
    ACTIVE,IN_ACTIVE;

    public static boolean validate(String status) {
        for(DriverStatus driverStatus: DriverStatus.values()){
            if(status.equalsIgnoreCase(driverStatus.name())){
                return true;
            }
        }
        return false;
    }
}
