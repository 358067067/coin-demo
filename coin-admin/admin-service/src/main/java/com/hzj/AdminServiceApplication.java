package com.hzj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AdminServiceApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AdminServiceApplication.class).run(args);
    }
}
