package ru.job4j.urlshortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

/**
 * ReqUrlDTO - объект, содержащий в себе URL адрес, полученный со стороны клиента
 *
 * @author Ilya Kaltygin
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqUrlDTO {

    @Pattern(regexp = "^https?://[^\\s/$.?#]+\\.[^\\s]*$", message = "Invalid URL")
    private String url;
}
