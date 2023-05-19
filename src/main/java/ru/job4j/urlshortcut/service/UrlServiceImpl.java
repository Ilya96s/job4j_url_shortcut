package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.urlshortcut.dto.ReqUrlDTO;
import ru.job4j.urlshortcut.dto.RespUrlDTO;
import ru.job4j.urlshortcut.dto.StatisticUrlDTO;
import ru.job4j.urlshortcut.mapper.StatisticUrlMapper;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.repository.SiteRepository;
import ru.job4j.urlshortcut.repository.UrlRepository;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

/**
 * UrlServiceImpl - реализация сервиса по работе с ссылками
 *
 * @author Ilya Kaltygin
 */
@Service
@AllArgsConstructor
public class UrlServiceImpl implements UrlService {

    /**
     * Хранилище ссылок
     */
    private final UrlRepository urlRepository;

    /**
     * Хранилище сайтов
     */
    private final SiteRepository siteRepository;

    private static final String HASHING_ALGORITHM = "SHA-256";

    /**
     * Преобразовать полный адрес в сокращенный. Если полный URL уже существует в базе данных,
     * то клиенту вернется уже существующий сокращенный URL. Иначе будет сгенерирован новый сокращенный URL
     *
     * @param reqUrlDTO объект типа ReqUrlDTO, содержащий полный адрес
     * @param auth объект, содержащий информацию о подлинности пользователя
     * @return объект типа RespUrlDTO, содержащий в себе сокращенный адрес
     */
    @SneakyThrows
    @Override
    public RespUrlDTO convert(ReqUrlDTO reqUrlDTO, Authentication auth) {
        var longUrl = urlRepository.findByLongUrl(reqUrlDTO.getUrl());
        if (longUrl.isEmpty()) {
            MessageDigest md = MessageDigest.getInstance(HASHING_ALGORITHM);
            byte[] hash = md.digest(reqUrlDTO.getUrl().getBytes());
            String base64encoded = Base64.getUrlEncoder().withoutPadding().encodeToString(hash).substring(0, 8);
            save(urlOf(reqUrlDTO.getUrl(), base64encoded, auth));
            return new RespUrlDTO(base64encoded);
        }
        return new RespUrlDTO(longUrl.get().getShortUrl());
    }

    /**
     * Сохранить объект Url в базу данных
     *
     * @param url объект типа Url
     * @return Optional.of(url) если url сохранен успешно, иначе Optional.empty();
     */
    @Override
    public Optional<Url> save(Url url) {
        return Optional.of(urlRepository.save(url));
    }

    /**
     * Найти объект Url в базе данных по полному адресу
     *
     * @param longUrl полный адрес
     * @return Optional.of(url) если объект Url найден в базе данных, иначе Optional.empty()
     */
    @Override
    public Optional<Url> findByLongUrl(String longUrl) {
        return urlRepository.findByLongUrl(longUrl);
    }

    /**
     * Найти объект Url в базе данных по сокращенному адресу
     *
     * @param shortUrl сокращенный адрес
     * @return Optional.of(url) если объект Url найден в базе данных, иначе Optional.empty()
     */
    @Override
    @Transactional
    public Optional<Url> findByShortUrl(String shortUrl) {
        var byShortUrl = urlRepository.findByShortUrl(shortUrl);
        byShortUrl.ifPresent(url -> urlRepository.updateUrlCounter(url.getId()));
        return byShortUrl;
    }

    /**
     * Найти все адреса в базе данных по идентификатору сайта
     *
     * @param auth объект, содержащий информацию о подлинности пользователя
     * @return список адресов
     */
    @Override
    public List<StatisticUrlDTO> findUrlBySite(Authentication auth) {
        var siteLogin = auth.getPrincipal().toString();
        var site = siteRepository.findByLogin(siteLogin);
        var urlList = urlRepository.findUrlBySiteId(site.get().getId());
        return urlList.stream()
                .map(StatisticUrlMapper::toStatisticUrlDTO)
                .toList();
    }

    /**
     * Создать объект типа Url по переданному полному адресу, сокращенному адресу
     * и полученному сайту из SecurityContextHolder
     *
     * @param longUrl  полный адрес
     * @param shortUrl сокращенный адрес
     * @param auth объект, содержащий информацию о подлинности пользователя
     * @return объект типа Url
     */
    private Url urlOf(String longUrl, String shortUrl, Authentication auth) {
        var siteLogin = auth.getPrincipal().toString();
        var optionalSite = siteRepository.findByLogin(siteLogin);
        return Url.builder()
                .longUrl(longUrl)
                .shortUrl(shortUrl)
                .site(optionalSite.get())
                .build();
    }
}
