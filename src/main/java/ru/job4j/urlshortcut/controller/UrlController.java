package ru.job4j.urlshortcut.controller;

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
 * UrlController - контроллер, отвечающий за обработку операций с Url
 *
 * @author Ilya Kaltygin
 */
@RestController
@AllArgsConstructor
public class UrlController {

    /**
     * Сервис по работе с ссылками
     */
    private final UrlService urlService;

    /**
     * POST метод для преобразования полного URL в сокращенный. Если полный URL уже существует в базе данных,
     * то клиенту вернется уже существующий сокращенный URL. Иначе будет сгенерирован новый сокращенный URL
     *
     * @param reqUrlDTO объект типа UrlDTO, содержащий полный адрес
     * @return сокращенный адрес, который можно использовать для доступа к полному адресу
     */
    @PostMapping("/convert")
    public ResponseEntity<RespUrlDTO> convertUrl(@Validated @RequestBody ReqUrlDTO reqUrlDTO) {
        var shortUrl = urlService.convert(reqUrlDTO);
        return ResponseEntity.status(HttpStatus.OK).body(shortUrl);
    }

    /**
     * GET метод возвращающий полный адрес по сокращенному
     *
     * @param shortUrl сокращенный адрес
     * @return Если для короткого адреса нет ассоциированного полного адреса, то выбрасывается исключение ResponseStatusException
     * Иначе возвращает ответ со статусом 302 FOUND и заголовком Location, который содержит полный адрес куда должен быть выполнен переход
     */
    @GetMapping("/redirect/{shortUrl}")
    public ResponseEntity<Void> getRedirect(@PathVariable String shortUrl) {
        var url = urlService.findByShortUrl(shortUrl)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No associations found for short url"));
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(url.getLongUrl()))
                .build();
    }

    /**
     * GET сетод возвращающий статистику всех адресов и количество вызовов этого адреса
     *
     * @return список объектов StatisticUrlDTO
     */
    @GetMapping("/statistic")
    public List<StatisticUrlDTO> getStatistic() {
        return urlService.findUrlBySite();
    }

}
