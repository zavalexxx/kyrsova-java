package com.example.eschool.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition (
        info = @Info(
                description = "Система для ведення оцінок, відвідуваності та планування уроків",
                title = "Електронний журнал для вчителів",
                version = "1.0"
        ),
        servers = {
                @Server(
                        description = "Local",
                        url = "http://localhost:8080"
                )
        }
)

public class SwaggerConfig {

}
