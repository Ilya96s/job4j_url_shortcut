package ru.job4j.urlshortcut.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SwaggerConfig - конфигурационный класс для Swagger
 *
 * @author Ilya Kaltytin
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Url shortcut API")
                                .version("1.0")
                                .contact(
                                        new Contact()
                                                .email("ilya_kaltygin@mail.ru")
                                                .name("Kaltygin Ilya")
                                )
                );
    }
}
