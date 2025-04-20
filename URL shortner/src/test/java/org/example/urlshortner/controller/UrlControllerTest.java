package org.example.urlshortner.controller;

import org.example.urlshortner.model.Url;
import org.example.urlshortner.service.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlControllerTest {

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public UrlService urlService() {
            return Mockito.mock(UrlService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UrlService urlService;

    private Url testUrl;
    private final String originalUrl = "https://www.example.com";
    private final String shortUrl = "abc123";

    @BeforeEach
    void setUp() {
        testUrl = new Url(originalUrl, shortUrl);
        testUrl.setId(1L);
    }

    @Test
    void showHomePage_ShouldReturnIndexPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("url"));
    }

    @Test
    void shortenUrl_ShouldReturnResultPage() throws Exception {
        when(urlService.generateShortUrl(anyString())).thenReturn(testUrl);

        mockMvc.perform(post("/shorten")
                .param("originalUrl", originalUrl))
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attributeExists("url"))
                .andExpect(model().attributeExists("host"))
                .andExpect(model().attribute("url", testUrl));
    }

    @Test
    void redirectToOriginalUrl_WhenUrlExists_ShouldRedirect() throws Exception {
        when(urlService.getOriginalUrl(shortUrl)).thenReturn(Optional.of(testUrl));

        mockMvc.perform(get("/" + shortUrl))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(originalUrl));
    }

    @Test
    void redirectToOriginalUrl_WhenUrlDoesNotExist_ShouldRedirectToHomePage() throws Exception {
        when(urlService.getOriginalUrl("nonexistent")).thenReturn(Optional.empty());

        mockMvc.perform(get("/nonexistent"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
