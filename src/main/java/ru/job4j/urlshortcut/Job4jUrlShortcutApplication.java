package ru.job4j.urlshortcut;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Job4jUrlShortcutApplication - конфигурационный класс приложения
 *
 * @author Ilya Kaltygin
 */
@SpringBootApplication
public class Job4jUrlShortcutApplication {

    /**
     * Запускает Spring-Boot приложение
     *
     * @param args массив аргументов командной строки
     */
    public static void main(String[] args) {
        SpringApplication.run(Job4jUrlShortcutApplication.class, args);
    }

}
