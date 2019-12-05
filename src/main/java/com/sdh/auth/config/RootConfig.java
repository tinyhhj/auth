package com.sdh.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AuthConfig.class, SecurityConfig.class,OpenApiConfig.class})
public class RootConfig {
}
