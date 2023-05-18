package ru.job4j.urlshortcut.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

/**
 * @author Ilya Kaltygin
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Объект, содержащий полный URL адрес")
public class ReqUrlDTO {

    @Pattern(regexp = "^https?://[^\\s/$.?#]+\\.[^\\s]*$", message = "Invalid URL")
    @Schema(
            description = "Полный URL адрес",
            example = "https://job4j.ru/profile/exercise/106/task-view/532")
    private String url;
}
