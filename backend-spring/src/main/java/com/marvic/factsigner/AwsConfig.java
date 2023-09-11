package com.marvic.factsigner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;

@Configuration
public class AwsConfig {

    @Bean
    public Region getRegion() {
        return Region.US_EAST_1;
    }



}
