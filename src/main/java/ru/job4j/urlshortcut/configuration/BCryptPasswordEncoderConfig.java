package ru.job4j.urlshortcut.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * BCryptPasswordEncoderConfig - конфигурационный класс. Для создания бина BCryptPasswordEncoder
 *
 * @author Ilya Kaltygin
 */
@Configuration
public class BCryptPasswordEncoderConfig {

    /**
     * Создает бин BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
