package com.vendormodule.Vendor.Module.config;  //Path at which this file  is saved 

// configuration related classes
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;  //used to encrypt password
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //tells that this class hs configurations
public class AppConfig implements WebMvcConfigurer { //customize the spring MVC like overriding 

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //hash user password before storing in db
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve static resources from classpath  serve all the resources 
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/", "classpath:/public/")
                .setCachePeriod(3600);  //catches file for 3600 sec in browser

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
    }
}