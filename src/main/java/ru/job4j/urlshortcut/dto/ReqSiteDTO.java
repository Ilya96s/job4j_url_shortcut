package ru.job4j.urlshortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

/**
 * ReqSiteDTO - объект, содержащий в себе домен сайта. Данные отправляются со стороны клиента
 *
 * @author Ilya Kaltygin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqSiteDTO {

    @Pattern(regexp = "^(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}$",
            message = "Invalid domain")
    private String domain;
}
