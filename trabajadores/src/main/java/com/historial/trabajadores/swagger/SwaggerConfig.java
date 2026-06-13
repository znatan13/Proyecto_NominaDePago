package com.historial.trabajadores.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI empleadoOpenAPI(){
        return new OpenAPI().info(new Info()
        .title("API Historial De Empleados")
        .description("API REST para microservicio historial de empleado")
        .version("1.0")
        .contact(new Contact()
                .name("Coreplay")
                .email("manuel@gmail.com")
            )
        );
    }

}
