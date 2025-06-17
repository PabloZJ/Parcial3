package com.tech_nova.tech_nova.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(
            new Info()
            .title("Api administración de tienda TechNova")
            .version("1.0")
            .description("Con está API se podrá administrar la pagina de ventas de la startup Technova, está permite la vista, creación, edición y eliminación de los distintos microservicios.")
        );
    }
}
