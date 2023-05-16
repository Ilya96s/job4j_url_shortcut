package ru.job4j.urlshortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.model.Site;

import java.util.Optional;

/**
 * SiteRepository - хранилище сайтов
 *
 * @author Ilya Kaltygin
 */
public interface SiteRepository extends CrudRepository<Site, Integer> {

    /**
     * Найти сайт в базе данных по домену
     *
     * @param domain домен сайта
     * @return Optional.of(site) если сайт по заданному домену найден в базе данных, иначе Optional.empty()
     */
    Optional<Site> findByDomain(String domain);

    /**
     * Найти сайт в базе данных по логину
     *
     * @param login логин
     * @return Optional.of(site) если сайт по заданному логину найден в базе данных, иначе Optional.empty()
     */
    Optional<Site> findByLogin(String login);
}
