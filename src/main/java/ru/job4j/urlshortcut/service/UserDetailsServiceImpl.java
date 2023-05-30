package ru.job4j.urlshortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.urlshortcut.model.Site;
import ru.job4j.urlshortcut.repository.SiteRepository;

import java.util.Optional;

import static java.util.Collections.emptyList;

/**
 * UserDetailsServiceImpl - сервис загружает в SecurityContextHolder детали авторизованного пользователя
 *
 * @author Ilya Kaltygin
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Хранилище сайтов
     */
    private final SiteRepository siteRepository;

    /**
     * Метод находит сайт в базе данных
     *
     * @param login логин
     * @return Если сайт не найден, выбрасывается исключение UsernameNotFoundException.
     * Иначе возвращается объект типа User, содержащий логин и пароль найденного сайта.
     * @throws UsernameNotFoundException exception
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Site> site = siteRepository.findByLogin(login);
        if (site.isEmpty()) {
            throw new UsernameNotFoundException(login);
        }
        return new User(site.get().getLogin(), site.get().getPassword(), emptyList());
    }
}
