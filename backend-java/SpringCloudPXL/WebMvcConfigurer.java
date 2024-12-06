//package com.example.demo.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // Enable CORS for all endpoints
//        registry.addMapping("/**")  // Allow all endpoints
//                .allowedOrigins("http://localhost:8083")  // Allow requests from your Vue frontend
//                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Allow these methods
//                .allowedHeaders("*")  // Allow all headers
//                .allowCredentials(true);  // Allow cookies, if needed
//    }
//}
//
