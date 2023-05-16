package ru.job4j.urlshortcut.service;

import ru.job4j.urlshortcut.dto.StatisticUrlDTO;
import ru.job4j.urlshortcut.dto.UrlDTO;
import ru.job4j.urlshortcut.model.Url;

import java.util.List;
import java.util.Optional;

/**
 * UrlService - интерфейс, описывающий бизнес логику по работе с ссылками
 *
 * @author Ilya Kaltygin
 */
public interface UrlService {

    /**
     * Преобразовать полный адрес в сокращенный
     *
     * @param urlDTO объект типа ReqUrlDTO, содержащий полный адрес
     * @return сокращенный адрес
     */
    String convert(UrlDTO urlDTO);

    /**
     * Сохранить объект Url в базу данных
     *
     * @param url объект типа Url
     * @return Optional.of(url) если url сохранен успешно, иначе Optional.empty();
     */
    Optional<Url> save(Url url);

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
     * Найти все адреса в базе данных по идентификатору сайта
     *
     * @return список адресов
     */
    List<StatisticUrlDTO> findUrlBySite();
}
