package ru.job4j.urlshortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * StatisticUrlDTO - объект, содержащий в себе полный адрес и количество вызовов данного адреса
 *
 * @author Ilya Kaltygin
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticUrlDTO {

    private String url;

    private int total;
}
