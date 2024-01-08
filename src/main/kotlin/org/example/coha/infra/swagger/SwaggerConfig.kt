package org.example.coha.infra.swagger

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
    class SwaggerConfig {

        @Bean
        fun openAPI(): OpenAPI = OpenAPI()
            .components(Components())
            .info(
                Info()
                    .title("Co-Ha")
                    .description("코린이의 셰어하우스")
                    .version("1.0.0")
            )
    }