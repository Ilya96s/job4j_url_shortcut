package ru.job4j.urlshortcut.service;

import ru.job4j.urlshortcut.dto.ReqSiteDTO;
import ru.job4j.urlshortcut.dto.RespSiteDTO;
import ru.job4j.urlshortcut.model.Site;

import java.util.Optional;

/**
 * SiteService - интерфейс, описывающий бизнес логику по работе с сайтами
 *
 * @author Ilya Kaltygin
 */
public interface SiteService {

    /**
     * Зарегистрировать сайт в базе данных
     *
     * @param reqSiteDTO объект типа ReqSiteDTO
     * @return Optional.of(respSiteDTO) если сайт успешно зарегистрирован в базе данных, иначе Optional.empty()
     */
    Optional<RespSiteDTO> registration(ReqSiteDTO reqSiteDTO);

    /**
     * Найти сайт в базе данных по домену
     *
     * @param domain домен сайте
     * @return Optional.of(site) если сайт по заданному домену найден в базе данных, иначе Optional.empty()
     */
    Optional<Site> findSiteByDomain(String domain);

    /**
     * Найти сайт в базе данных по логину
     *
     * @param login логин
     * @return Optional.of(sit) если сайт по заданному логину найден в базе данных, иначе Optional.empty()
     */
    Optional<Site> findSiteByLogin(String login);
}
