package org.wherecamp.hackathon.phumblr.service;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Created by kilobike on 24.02.16.
 */
@Configuration
@ConfigurationProperties
@ComponentScan
public class DataBaseConfig {

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() {
        DataSource ds = DataSourceBuilder.create().build();
        return ds;
    }
}

