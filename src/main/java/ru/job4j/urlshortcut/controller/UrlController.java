package ru.job4j.urlshortcut.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.urlshortcut.dto.RespUrlDTO;
import ru.job4j.urlshortcut.dto.StatisticUrlDTO;
import ru.job4j.urlshortcut.dto.ReqUrlDTO;
import ru.job4j.urlshortcut.service.UrlService;

import java.net.URI;
import java.util.List;

/**
 * @author Ilya Kaltygin
 */
@RestController
@AllArgsConstructor
@Tag(name = "Контроллер для работы с URL адресами")
public class UrlController {

    /**
     * Сервис по работе с ссылками
     */
    private final UrlService urlService;

    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Преобразование полного URL адреса в сокращенный URL адрес.",
            description = "Если полный URL уже существует в базе данных," +
                    "то клиенту вернется уже существующий сокращенный URL. " +
                    "Иначе будет сгенерирован новый сокращенный URL адрес, " +
                    "который можно использовать для доступа к полному URL адресу"
    )
    @PostMapping("/convert")
    public ResponseEntity<RespUrlDTO> convertUrl(
            @Validated @RequestBody @Parameter(description = "Объект, содержащий полный URL адрес") ReqUrlDTO reqUrlDTO) {
        var shortUrl = urlService.convert(reqUrlDTO);
        return ResponseEntity.status(HttpStatus.OK).body(shortUrl);
    }

    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Переадресация на полный URL адрес",
            description = "Если для короткого адреса нет ассоциированного полного адреса, " +
                    "то выбрасывается исключение ResponseStatusException." +
                    "Иначе возвращает ответ со статусом 302 FOUND и заголовком Location, " +
                    "который содержит полный адрес куда должен быть выполнен переход"
    )
    @GetMapping("/redirect/{shortUrl}")
    public ResponseEntity<Void> getRedirect(
            @PathVariable @Parameter(description = "Сокращенный URL адрес") String shortUrl) {
        var url = urlService.findByShortUrl(shortUrl)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No associations found for short url"));
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(url.getLongUrl()))
                .build();
    }

    @SecurityRequirement(name = "JWT")
    @Operation(
            summary = "Получение статистики для всех адресов и количество вызовов каждого адреса",
            description = "Метод возвращает статистику для всех адресов и количество вызово каждого адреса."
    )
    @GetMapping("/statistic")
    public List<StatisticUrlDTO> getStatistic() {
        return urlService.findUrlBySite();
    }

}
