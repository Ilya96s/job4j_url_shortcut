package ru.job4j.urlshortcut.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SwaggerConfig - конфигурационный класс для Swagger
 *
 * @author Ilya Kaltytin
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Url shortcut API",
                description = "Сервис для генерации сокращенных URL адресов", version = "1.0",
                contact = @Contact(
                        name = "Kaltygin Ilya",
                        email = "ilya_kaltygin@mail.ru"
                )
        )
)
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {
}
