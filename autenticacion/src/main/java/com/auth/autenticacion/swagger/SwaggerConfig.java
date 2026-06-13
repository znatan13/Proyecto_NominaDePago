package com.auth.autenticacion.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI autenticacionAPI(){

        return new OpenAPI().info (new Info().title("API autenticacion")
        .description("API REST para autenticacion documentado ")
        .version("1.0")
        .contact(new Contact()
                .name("coreplay")
                .email("manuel@gmail.com")
        )
    );
        
    }

}
