package com.santech.mtm.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task App API")
                        .description("""
                                REST API to manage a task list per user.
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Julian Pellegrini & Santiago Fidelibus")
                                .email("pellegrinijulianmauro@gmail.com, santifidelibus19@gmail.com")
                                .url("https://github.com/SantiagoFidelibus")
                        )
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Swagger/OpenAPI Reference")
                        .url("https://swagger.io/docs/"))
                .addTagsItem(new Tag().name("Users").description("Manage user accounts"))
                .addTagsItem(new Tag().name("Tasks").description("Manage user tasks"));
    }
}