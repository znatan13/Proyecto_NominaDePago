package com.control.asistencia.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI asistenciaApi(){
        return new OpenAPI().info(new Info()
                .title("API REST control Asistencia")
                .description("API de microservicio control asistencia documentado")
                .version("1.0")
                .contact(new Contact()
                        .name("Manuel mora")
                        .email("manuel@gmail.com"))
            );
    }
}
