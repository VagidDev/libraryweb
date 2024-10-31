package com.portfolio.libraryweb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:smtp.properties")
public class AppConfig {
}
