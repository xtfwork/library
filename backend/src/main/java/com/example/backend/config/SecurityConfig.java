package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain sfc(HttpSecurity http)throws Exception{

        http
            .csrf(csrf -> csrf.disable())

             .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/**", "/api/books/**", "/api/auth/register")
                    .permitAll()
                .requestMatchers("/api/borrow", "/api/return", "/api/borrows")
                    .authenticated()
                .anyRequest()
                    .permitAll()
            )

            .httpBasic(basic -> {});

        return http.build();

    }
    
}
