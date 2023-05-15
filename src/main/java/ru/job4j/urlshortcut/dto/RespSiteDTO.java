package ru.job4j.urlshortcut.dto;

import lombok.Builder;
import lombok.Data;

/**
 * RespSiteDTO - объект, содержащий в себе логин, пароль и статус регистрации.
 * Данные генерируются на стороне сервера и отправляются клиенту
 *
 * @author Ilya Kaltygin
 */
@Data
@Builder
public class RespSiteDTO {

    private boolean registration;

    private String login;

    private String password;

}
