package ru.job4j.urlshortcut.dto;

import lombok.Builder;
import lombok.Data;

/**
 * UrlDTO - объект, содержащий в себе URL адрес, полученный со стороны клиента
 *
 * @author Ilya Kaltygin
 */
@Data
@Builder
public class UrlDTO {

    private String url;
}
