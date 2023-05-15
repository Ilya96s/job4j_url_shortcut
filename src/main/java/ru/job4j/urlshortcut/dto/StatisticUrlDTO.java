package ru.job4j.urlshortcut.dto;

import lombok.Builder;
import lombok.Data;

/**
 * StatisticUrlDTO - объект, содержащий в себе полный адрес и количество вызовов данного адреса
 *
 * @author Ilya Kaltygin
 */
@Builder
@Data
public class StatisticUrlDTO {

    private String url;

    private int total;
}
