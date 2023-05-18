package ru.job4j.urlshortcut.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.urlshortcut.dto.ReqSiteDTO;
import ru.job4j.urlshortcut.dto.RespSiteDTO;
import ru.job4j.urlshortcut.service.SiteService;

/**
 * @author Ilya Kaltygin
 */
@RestController
@AllArgsConstructor
@Tag(name = "Контроллер для работы с сайтами")
public class SiteController {

    /**
     * Сервис по работе с сайтами
     */
    private final SiteService service;

    @Operation(
            summary = "Регистрация сайта",
            description = "Если сайт ранее не был зарегистрирован в базе даннх,"
                    + "то клиенту вернется ответ в виде {registration : true, login: УНИКАЛЬНЫЙ_ЛОГИН, password : УНИКАЛЬНЫЙ_ПАРОЛЬ}."
                    + "Иначе ответ в виде {registration : false, login: УНИКАЛЬНЫЙ_КОД, password : УНИКАЛЬНЫЙ_КОД}"
    )
    @PostMapping("/registration")
    public ResponseEntity<RespSiteDTO> registration(
            @Validated @RequestBody @Parameter(description = "Объект, содержащий домен сайта") ReqSiteDTO reqSiteDTO) {
        var respSiteDTO = service.registration(reqSiteDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(respSiteDTO.get());
    }
}
