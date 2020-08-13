package com.zzvcom.stat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan({"com.zzvcom.stat.business.**.mapper"})
public class StatApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatApplication.class, args);
    }

}
