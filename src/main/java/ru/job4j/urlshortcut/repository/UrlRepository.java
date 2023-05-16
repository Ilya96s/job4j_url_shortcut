package ru.job4j.urlshortcut.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.urlshortcut.model.Url;

import java.util.List;
import java.util.Optional;

/**
 * UrlRepository - хранилище ссылок
 *
 * @author Ilya Kaltygin
 */
public interface UrlRepository extends CrudRepository<Url, Integer> {


    /**
     * Найти объект Url в базе данных по полному адресу
     *
     * @param longUrl полный адрес
     * @return Optional.of(url) если объект Url найден в базе данных, иначе Optional.empty()
     */
    Optional<Url> findByLongUrl(String longUrl);

    /**
     * Найти объект Url в базе данных по сокращенному адресу
     *
     * @param shortUrl сокращенный адрес
     * @return Optional.of(url) если объект Url найден в базе данных, иначе Optional.empty()
     */
    Optional<Url> findByShortUrl(String shortUrl);

    /**
     * Обновить счетчик Url
     *
     * @param id идентификатор Url
     */
    @Modifying
    @Query("UPDATE Url as u SET u.count = u.count + 1 WHERE u.id = :id")
    void updateUrlCounter(int id);

    /**
     * Найти все адреса в базе данных по идентификатору сайта
     *
     * @param siteId идентификатор сайта
     * @return список адресов
     */
    List<Url> findUrlBySiteId(int siteId);

}
