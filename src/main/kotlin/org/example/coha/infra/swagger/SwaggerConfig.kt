package org.example.coha.infra.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
    class SwaggerConfig {
        @Bean
        fun openAPI(): OpenAPI = OpenAPI()
            .components(Components()
                .addSecuritySchemes("Co-Ha-Security", SecurityScheme()
                    .name("Co-Ha-Security")
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")))
            .addSecurityItem(SecurityRequirement().addList("Co-Ha-Security"))
            .info(
                Info()
                    .title("Co-Ha")
                    .description("코린이의 셰어하우스")
                    .version("1.0.0")
            )
    }