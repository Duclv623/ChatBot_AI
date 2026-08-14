package com.jurisai.config;

import com.jurisai.util.SnowflakeIdGenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowflakeConfig {
    @Value("${snowflake.worker-id}")
    private long workerId;

    @Value("${snowflake.datacenter-id}")
    private long datacenterId;

    @Bean
    public SnowflakeIdGenUtils snowflakeIdGenerator(){
        return new SnowflakeIdGenUtils(workerId, datacenterId);
    }
}
