package ru.job4j.urlshortcut.mapper;

import ru.job4j.urlshortcut.dto.StatisticUrlDTO;
import ru.job4j.urlshortcut.model.Url;

/**
 * StatisticUrlMapper - преобразует объект типа Url в StatisticUrlDTO
 *
 * @author Ilya kaltygin
 */
public class StatisticUrlMapper {

    /**
     * Преобразует объект типа Url в StatisticUrlDTO
     *
     * @param url объект типа Url
     * @return Объект типа StatisticUrlDTO
     */
    public static StatisticUrlDTO toStatisticUrlDTO(Url url) {
        return StatisticUrlDTO.builder()
                .url(url.getLongUrl())
                .total(url.getCount())
                .build();
    }
}
