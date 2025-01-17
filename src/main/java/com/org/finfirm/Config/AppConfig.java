package com.org.finfirm.Config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc // Enables Spring MVC
@ComponentScan(basePackages = "com.org.finfirm")
@Import({DatabaseConfig.class,SecurityConfig.class})
public class AppConfig {
}
