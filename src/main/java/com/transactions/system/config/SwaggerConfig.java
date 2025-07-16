package com.transactions.system.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${swagger.server.local}")
    private String localUrl;

    @Value("${spring.profiles.active:local}")
    private String activeProfile;

    @Bean
    public OpenAPI customOpenAPI() {
        List<Server> servers = new ArrayList<>();

        if ("local".equalsIgnoreCase(activeProfile)) {
            servers.add(new Server().url(localUrl).description("Local Server"));
        }

        return new OpenAPI()
                .info(new Info()
                        .title("API Sistema de transacciones y pagos")
                        .version("1.0.0")
                        .description("API para gestión de transacciones y pagos")
                )
                .servers(servers);
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .pathsToMatch("/**")
                .build();
    }
}
