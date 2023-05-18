package ru.job4j.urlshortcut.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ilya Kaltygin
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Объект, содержащий логин, пароль и статус регистрации")
public class RespSiteDTO {

    @Schema(description = "Статус регистрации")
    private boolean registration;

    @Schema(description = "Логин")
    private String login;

    @Schema(description = "Пароль")
    private String password;

}
