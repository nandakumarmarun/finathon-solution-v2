package com.org.finfirm.Config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.org.finfirm")
@Import(DatabaseConfig.class)
public class AppConfig {
}
