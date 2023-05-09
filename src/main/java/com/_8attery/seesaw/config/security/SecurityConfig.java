package com._8attery.seesaw.config.security;

import com._8attery.seesaw.config.util.JwtTokenUtil;
import com._8attery.seesaw.filter.jwt.JwtAuthenticationFilter;
import com._8attery.seesaw.filter.jwt.JwtExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 기본 웹보안 사용
@RequiredArgsConstructor
@ConditionalOnDefaultWebSecurity
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityConfig {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;


    public PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers(
                        "/h2-console/**",
                        "/health/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/swagger-ui/**");
    }


    @Bean
    @Order(SecurityProperties.BASIC_AUTH_ORDER)
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .formLogin().disable();

        return httpSecurity
                .csrf().disable()// CSRF 설정 Disable
                // exception handling 할 때 우리가 만든 클래스를 추가
                .exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .accessDeniedHandler(jwtAccessDeniedHandler)
                /* iframe 관련 설정이고 X-frame-Options Click Jaking 공격을 기본적으로 막는걸로 설정되어있는데
                 이를 풀기위한 설정을 하려면 아래의 설정을 추가하면 됨 */
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests() // http servletRequest 를 사용하는 요청들에 대한 접근제한을 설정
                .antMatchers("/auth/login", "/auth/regenerate-token", "/auth/sign-up", "/auth/test/generate-token").permitAll()
                .anyRequest().authenticated()   // 나머지 API 는 전부 인증 필요

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenUtil, authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class)

                .build();
    }

}
