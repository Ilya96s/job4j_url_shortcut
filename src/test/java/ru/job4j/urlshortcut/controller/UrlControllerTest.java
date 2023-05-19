package ru.job4j.urlshortcut.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.urlshortcut.Job4jUrlShortcutApplication;
import ru.job4j.urlshortcut.dto.ReqUrlDTO;
import ru.job4j.urlshortcut.dto.RespUrlDTO;
import ru.job4j.urlshortcut.model.Url;
import ru.job4j.urlshortcut.service.UrlService;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * UrlControllerTest - тесты для контроллера UrlController
 *
 * @author Ilya Kaltygin
 */
@SpringBootTest(classes = Job4jUrlShortcutApplication.class)
@AutoConfigureMockMvc
class UrlControllerTest {

    /**
     * Обеспечивает возможность отправки запрсоов на тестируемый контроллер и проверки его ответов
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Заглушка сервиса
     */
    @MockBean
    private UrlService urlService;

    @Test
    @WithMockUser
    void whenConvertCorrectLongUrlThenReturnShortUrl() throws Exception {

        var jsonObject = new JSONObject();
        jsonObject.put("url", "https://job4j.ru/profile/exercise/106/task-view/532");

        var respUrlDTO = new RespUrlDTO("ZRUfdD2");

        ArgumentCaptor<ReqUrlDTO> argumentCaptor = ArgumentCaptor.forClass(ReqUrlDTO.class);
        Mockito.when(urlService.convert(argumentCaptor.capture())).thenReturn(respUrlDTO);

        mockMvc.perform(post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("ZRUfdD2"));

        verify(urlService).convert(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().getUrl(),
                is("https://job4j.ru/profile/exercise/106/task-view/532"));
    }

    @Test
    @WithMockUser
    public void whenConvertIncorrectLongUrlThenReturnBadRequest() throws Exception {

        var jsonObject = new JSONObject();
        jsonObject.put("url", "job4j.ru");

        mockMvc.perform(post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void whenRedirectThenGetLongUrlAnd302Status() throws Exception {

        var shortUrl = "ZRUfdD2";

        var url = Url.builder()
                .longUrl("https://job4j.ru/profile/exercise/106/task-view/532")
                .build();

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.when(urlService.findByShortUrl(argumentCaptor.capture())).thenReturn(Optional.of(url));

        mockMvc.perform(get("/redirect/" + shortUrl))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(url.getLongUrl()));

        verify(urlService).findByShortUrl(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue(), is("ZRUfdD2"));
    }

    @Test
    @WithMockUser
    public void whenRedirectThenReturnNotFoundStatus() throws Exception {

        var shortUrl = "ZRUfdD2";

        mockMvc.perform(get("/redirect/" + shortUrl))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void whenGetStatistic() throws Exception {

        mockMvc.perform(get("/statistic"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}