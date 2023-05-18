package ru.job4j.urlshortcut.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ilya Kaltygin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Объект, содержащий сокращенный URL адрес")
public class RespUrlDTO {

    @Schema(description = "Сокращенный URL адрес")
    private String code;
}
