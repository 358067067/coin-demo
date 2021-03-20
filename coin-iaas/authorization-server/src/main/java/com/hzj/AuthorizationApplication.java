package com.hzj;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class AuthorizationApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(AuthorizationApplication.class).run(args);
    }
}
