package com.henrique.chat_api.configurations;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", name = "bearer token")
public class SwaggerConfiguration {
    @Bean
    public OpenAPI config() {
        return new OpenAPI()
                .info(new Info()
                        .title("Chat API")
                        .description("Chat API Spring Documentation")
                );
    }
}