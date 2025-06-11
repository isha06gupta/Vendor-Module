package com.vendormodule.Vendor.Module.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve static resources from classpath
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/", "classpath:/public/")
                .setCachePeriod(3600);

        // Serve CSS files
        registry.addResourceHandler("/*.css")
                .addResourceLocations("classpath:/static/", "classpath:/public/")
                .setCachePeriod(3600);

        // Serve JS files
        registry.addResourceHandler("/*.js")
                .addResourceLocations("classpath:/static/", "classpath:/public/")
                .setCachePeriod(3600);

        // Serve image files
        registry.addResourceHandler("/*.jpg", "/*.jpeg", "/*.png", "/*.gif")
                .addResourceLocations("classpath:/static/", "classpath:/public/")
                .setCachePeriod(3600);
    }}
    