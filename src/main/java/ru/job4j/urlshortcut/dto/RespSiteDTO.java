package ru.job4j.urlshortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RespSiteDTO - объект, содержащий в себе логин, пароль и статус регистрации.
 * Данные генерируются на стороне сервера и отправляются клиенту
 *
 * @author Ilya Kaltygin
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespSiteDTO {

    private boolean registration;

    private String login;

    private String password;

}
