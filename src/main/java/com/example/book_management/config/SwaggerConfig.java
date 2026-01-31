package com.example.book_management.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Book Management API",
                version = "1.0",
                description = "Book Management System APIs"
        )
)
public class SwaggerConfig {
}
