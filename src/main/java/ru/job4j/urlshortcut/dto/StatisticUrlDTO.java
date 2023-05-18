package ru.job4j.urlshortcut.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ilya Kaltygin
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Объект, содержащий полный URL адрес и количество вызовов данного URL адреса")
public class StatisticUrlDTO {

    @Schema(description = "Полный URL адрес")
    private String url;

    @Schema(description = "Количество вызовов данного URL адреса")
    private int total;
}
