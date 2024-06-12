package com.mycompany;

import org.springframework.boot.actuate.hazelcast.HazelcastHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

//@Component
public class CustomHealthCheck implements HealthIndicator {

    int errorCode = 0;

    @Override
    public Health health() {
        // TODO Auto-generated method stub
        System.out.println("Health Checl error code is" + errorCode);
        if (errorCode > 2 && errorCode < 4) {
            errorCode++;
            return Health.down().withDetail("DOWN with error code", errorCode).build();
        } else {
            errorCode++;
            return Health.up().build();
        }
    }

}
