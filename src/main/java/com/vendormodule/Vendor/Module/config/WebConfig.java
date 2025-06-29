package com.vendormodule.Vendor.Module.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Handle static resources from /static and /public directories
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(3600)
                .resourceChain(true);
    
        registry.addResourceHandler("/public/**")
                .addResourceLocations("classpath:/public/")
                .setCachePeriod(3600)
                .resourceChain(true);
    
        // Handle CSS files
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/", "classpath:/public/css/")
                .setCachePeriod(3600)
                .resourceChain(true);
    
        // Handle JS files  
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/", "classpath:/public/js/")
                .setCachePeriod(3600)
                .resourceChain(true);
    
        // Handle image files
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/", "classpath:/public/images/")
                .setCachePeriod(3600)
                .resourceChain(true);
    
        // Handle HTML files and root resources
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/", "classpath:/public/")
                .setCachePeriod(0); // Don't cache HTML files
    
        // Handle uploaded documents
        registry.addResourceHandler("/documents/**")
                .addResourceLocations("file:/D:/OTHERS/HTML Files/Vendor Module/uploads/");
    
        // Handle vendor documents
        registry.addResourceHandler("/vendor_docs/**")
                .addResourceLocations("file:/D:/OTHERS/HTML Files/Vendor Module/vendor_docs/");
    }
}
