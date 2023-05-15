package ru.job4j.urlshortcut.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Site - модель данных, описывающая сайт
 *
 * @author Ilya Kaltygin
 */
@Data
@Builder
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Site {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Домен
     */
    private String domain;

    /**
     * Логин
     */
    private String login;

    /**
     * Пароль
     */
    private String password;
}
