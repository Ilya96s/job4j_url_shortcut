package ru.job4j.urlshortcut.dto;

import lombok.Builder;
import lombok.Data;

/**
 * ReqSiteDTO - объект, содержащий в себе домен сайта. Данные отправляются со стороны клиента
 *
 * @author Ilya Kaltygin
 */
@Data
@Builder
public class ReqSiteDTO {

    private String domain;
}
