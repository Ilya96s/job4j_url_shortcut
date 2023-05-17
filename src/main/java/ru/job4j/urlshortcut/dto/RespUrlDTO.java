package ru.job4j.urlshortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RespUrlDTO - объект, содержащий в себе сокращенный адрес
 *
 * @author Ilya Kaltygin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespUrlDTO {

    private String code;
}
