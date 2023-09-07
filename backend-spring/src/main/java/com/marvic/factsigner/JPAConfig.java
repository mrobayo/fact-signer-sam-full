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

    @Profile("prod")
    @Bean
    @ConfigurationProperties("spring.prod.datasource")
    public HikariDataSource dataSourceProd() {
        return DataSourceBuilder
                .create() .type(HikariDataSource.class).build();
    }

    @Profile("local")
    @Bean
    @ConfigurationProperties("spring.local.datasource")
    public HikariDataSource dataSourceLocal() {
        return DataSourceBuilder
                .create() .type(HikariDataSource.class).build();
    }

}
