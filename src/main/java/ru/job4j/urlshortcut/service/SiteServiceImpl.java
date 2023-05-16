package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.dto.ReqSiteDTO;
import ru.job4j.urlshortcut.dto.RespSiteDTO;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.repository.SiteRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class SiteServiceImpl implements SiteService {

    /**
     * Хранилище сайтов
     */
    private final SiteRepository siteRepository;

    /**
     * Объект, хэширующий пароль
     */
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Зарегистрировать сайт в базе данных
     *
     * @param reqSiteDTO объект типа ReqSiteDTO
     * @return Если сайт еще не зарегистрирован в базе данных,
     * то вернется Optional. of(RespSiteDTO) со сгенерированным логином, паролем и значением isReg = true.
     * Иначе Optional. of(RespSiteDTO) с паролем и логином, полученными из базы данных, и значением isReg = false.
     */
    @Override
    public Optional<RespSiteDTO> registration(ReqSiteDTO reqSiteDTO) {
        Optional<RespSiteDTO> result;
        var foundSite = findSiteByDomain(reqSiteDTO.getDomain());
        if (foundSite.isEmpty()) {
            var password = generatePassword();
            var login = generatedLogin();
            var site = Site.builder()
                    .domain(reqSiteDTO.getDomain())
                    .login(login)
                    .password(passwordEncoder.encode(password))
                    .build();
            var savedSite = siteRepository.save(site);
            result = Optional.of(RespSiteDTO.builder()
                    .registration(true)
                    .login(savedSite.getLogin())
                    .password(password)
                    .build());
        } else {
            result = Optional.of(RespSiteDTO.builder()
                    .registration(false)
                    .login(foundSite.get().getLogin())
                    .password(foundSite.get().getPassword())
                    .build());

        }
        return result;
    }

    /**
     * Найти сайт в базе данных по домену
     *
     * @param domain домен сайте
     * @return Optional.of(site) если сайт по заданному домену найден в базе данных, иначе Optional.empty()
     */
    @Override
    public Optional<Site> findSiteByDomain(String domain) {
        return siteRepository.findByDomain(domain);
    }

    /**
     * Найти сайт в базе данных по логину
     *
     * @param login логин
     * @return Optional.of(sit) если сайт по заданному логину найден в базе данных, иначе Optional.empty()
     */
    @Override
    public Optional<Site> findSiteByLogin(String login) {
        return siteRepository.findByLogin(login);
    }

    /**
     * Сгенерировать пароль
     */
    private static String generatePassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * Сгенерировать логин
     */
    private static String generatedLogin() {
        return RandomStringUtils.randomAlphabetic(8, 16);
    }
}
