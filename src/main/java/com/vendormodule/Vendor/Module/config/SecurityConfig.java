package com.vendormodule.Vendor.Module.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/", "/index.html", "/details.html", "/docupload.html", "/verified.html").permitAll()
            .requestMatchers("/static/**", "/public/**").permitAll()
            .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
            .requestMatchers("/*.html", "/*.css", "/*.js").permitAll()
            .requestMatchers("/documents/**", "/vendor_docs/**").permitAll()
            .requestMatchers("/api/vendor/**", "/api/clients/**").permitAll()
            .anyRequest().authenticated()
        );
    return http.build();
}
}
