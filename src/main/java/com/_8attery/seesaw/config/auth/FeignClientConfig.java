package com._8attery.seesaw.config.auth;

import com._8attery.seesaw.SeeSawApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = SeeSawApplication.class)
public class FeignClientConfig {
}
