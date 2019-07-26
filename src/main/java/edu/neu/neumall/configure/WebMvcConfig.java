package edu.neu.neumall.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
//@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedOrigins("http://localhost:8080");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        var currentPath = Paths.get(System.getProperty("user.dir"));
        var staticPath = Paths.get(currentPath.toString(), "static");
        var location = Paths.get(staticPath.toString(), "img");
        location = Path.of(location.toString(), "/");
        System.out.println(location);
        registry.addResourceHandler("/**").addResourceLocations("file:///" + location.toString());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
