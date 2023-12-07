package com.letter.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@OpenAPIDefinition(
        info = @Info(
                title = "'너는, 나는' API 명세서",
                description = "'너는, 나는' 프로젝트에 사용되는 API 명세서",
                version = "v1"
        )
)
@Configuration
public class SwaggerConfig {

    @Bean
    @Profile("!Prod")   // Product 환경에서는 Swagger 비활성화
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components());
    }
}
