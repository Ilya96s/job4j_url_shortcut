package ru.job4j.urlshortcut.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

/**
 * Url - модель данных, описывающая ссылку
 */
@Data
@Builder
@EqualsAndHashCode(of = "id")
@ToString(of = {"longUrl", "shortUrl", "count"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Url {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Ссылка полученная от клиента
     */
    private String longUrl;

    /**
     * Сокращенный вариант ссылки, полученной от клиента
     */
    private String shortUrl;

    /**
     * Счетчик, показывающий количество вызовов сокращенной ссылки
     */
    private int count;

    /**
     * Сайт
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
    private Site site;
}
