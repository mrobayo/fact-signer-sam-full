package com.marvic.factsigner;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

// @Profile("dev")
@Configuration
@EnableTransactionManagement
public class JPAConfig {

    @Profile("default")
    @Bean
    @ConfigurationProperties("spring.datasource")
    public HikariDataSource dataSourceDefault() {
        // out.println(" ******  ****** DEFAULT: H2 ****** ****** ");
        return DataSourceBuilder
                .create() .type(HikariDataSource.class) .build();
    }

    // AWS
    @Profile("prod")
    @Bean
    @ConfigurationProperties("spring.prod.datasource")
    public HikariDataSource dataSourceProd() {
        // out.println(" ******  ****** PROD: AWS ****** ****** ");
        return DataSourceBuilder
                .create() .type(HikariDataSource.class) .build();
    }

    // Intent for Docker
    @Profile("dev")
    @Bean
    @ConfigurationProperties("spring.dev.datasource")
    public HikariDataSource dataSourceDev() {
        // out.println(" ******  ****** DEV: DOCKER ****** ****** ");
        return DataSourceBuilder
                .create() .type(HikariDataSource.class) .build();
    }

    // Local PC
    @Profile("local")
    @Bean
    @ConfigurationProperties("spring.local.datasource")
    public HikariDataSource dataSourceLocal() {
        // out.println(" ******  ****** LOCAL: --- ****** ****** ");
        return DataSourceBuilder
                .create() .type(HikariDataSource.class).build();
    }

}
