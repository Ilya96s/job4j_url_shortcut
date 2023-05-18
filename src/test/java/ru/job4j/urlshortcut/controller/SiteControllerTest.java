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
import ru.job4j.urlshortcut.dto.ReqSiteDTO;
import ru.job4j.urlshortcut.dto.RespSiteDTO;
import ru.job4j.urlshortcut.service.SiteService;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * SiteControllerTest - тесты для контроллера SiteController
 *
 * @author Ilya Kaltygin
 */
@SpringBootTest(classes = Job4jUrlShortcutApplication.class)
@AutoConfigureMockMvc
class SiteControllerTest {

    /**
     * Обеспечивает возможность отправки запрсоов на тестируемый контроллер и проверки его ответов
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Заглушка сервиса
     */
    @MockBean
    private SiteService siteService;

    @Test
    @WithMockUser
    public void whenRegCorrectDomainThenReturnRestSiteDTOAndStatusCreated() throws Exception {

        var jsonObject = new JSONObject();
        jsonObject.put("domain", "job4j.ru");

        var respSiteDTO = RespSiteDTO.builder()
                .registration(true)
                .login("login123")
                .password("password123")
                .build();

        ArgumentCaptor<ReqSiteDTO> argumentCaptor = ArgumentCaptor.forClass(ReqSiteDTO.class);
        Mockito.when(siteService.registration(argumentCaptor.capture())).thenReturn(Optional.of(respSiteDTO));

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("registration").value(true))
                .andExpect(jsonPath("login").value("login123"))
                .andExpect(jsonPath("password").value("password123"));

        verify(siteService).registration(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().getDomain(), is("job4j.ru"));
    }

    @Test
    @WithMockUser
    public void whenRegIncorrectDomainThenReturnBadRequest() throws Exception {
        var jsonObject = new JSONObject();
        jsonObject.put("domain", "job4j");

        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isBadRequest());
    }
}