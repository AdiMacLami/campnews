package net.ictcampus.campnews.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        // Welche URL darf auf das Backend zugreifen? Können auch IP's sein
                        .allowedOrigins("http://localhost:3000", "http://172.16.2.108:3000")
                        // Mit welcher HTTP Function darf zugegriffen werden?
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        // Welche Header werden akzeptiert? Bspw. Token kommen in Authorization. * ist eine Wildcard
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}