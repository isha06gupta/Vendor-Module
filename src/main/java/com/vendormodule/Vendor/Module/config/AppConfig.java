package com.vendormodule.Vendor.Module.config;

// configuration related classes
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.lang.NonNull;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //hash user password before storing in db
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve static resources from classpath  serve all the resources 
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
    }
}