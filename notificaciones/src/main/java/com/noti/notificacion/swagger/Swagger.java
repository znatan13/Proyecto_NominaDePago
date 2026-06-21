package com.noti.notificacion.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class Swagger {

    @Bean
    public OpenAPI noficacionApi(){
        return new OpenAPI().info(new Info().title("API notificacion")
        .description("API REST para notificacion documentada")
        .version("1.0").contact(new Contact().name("matias")
        .email("duran&maty@gmail.com")));
    }
}
