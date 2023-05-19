package ru.job4j.urlshortcut.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.job4j.urlshortcut.filter.JWTAuthenticationFilter;
import ru.job4j.urlshortcut.filter.JWTAuthorizationFilter;
import ru.job4j.urlshortcut.service.SiteServiceImpl;

import static ru.job4j.urlshortcut.filter.JWTAuthenticationFilter.SIGN_UP_URL;

/**
 * WebSecurityConfig - конфигурационный класс для настрйоки Spring Security
 *
 * @author Ilya Kaltygin
 */
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Сервис, загружающий детали авторизованного пользователя в SecurityContextHolder
     */
    private SiteServiceImpl siteService;

    /**
     * Объект, хэширующий пароли
     */
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Устанавливает настройки безопасности для запросов, указывает правила аутентификации и авторизации
     *
     * @param http используется для настройки различных аспектов безопасности приложения
     * @throws Exception exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.GET, "/redirect/**").permitAll()
                .antMatchers(HttpMethod.GET, "/v3/api-docs").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * Определяет сервис для обработки запросов аутентификации пользователей
     *
     * @param auth предоставляет возможность настраивать способы аутентификации пользователей
     * @throws Exception exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(siteService).passwordEncoder(bCryptPasswordEncoder);
    }

    /**
     * Создает бин CorsConfigurationSource
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
