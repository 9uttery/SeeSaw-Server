package com._8attery.seesaw.config.util;

import com._8attery.seesaw.config.resolver.ClientIpResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final ClientIpResolver clientIpResolver;

    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(clientIpResolver);
    }
}
