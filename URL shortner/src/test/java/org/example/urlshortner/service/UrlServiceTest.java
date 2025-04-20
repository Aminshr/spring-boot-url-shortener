package org.example.urlshortner.service;

import org.example.urlshortner.model.Url;
import org.example.urlshortner.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
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
    void generateShortUrl_ShouldCreateAndSaveUrl() {
        // Arrange
        when(urlRepository.save(any(Url.class))).thenReturn(testUrl);

        // Act
        Url result = urlService.generateShortUrl(originalUrl);

        // Assert
        assertNotNull(result);
        assertEquals(originalUrl, result.getOriginalUrl());
        assertEquals(shortUrl, result.getShortUrl());
        verify(urlRepository, times(1)).save(any(Url.class));
    }

    @Test
    void getOriginalUrl_WhenUrlExists_ShouldReturnUrl() {
        // Arrange
        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.of(testUrl));

        // Act
        Optional<Url> result = urlService.getOriginalUrl(shortUrl);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(originalUrl, result.get().getOriginalUrl());
        verify(urlRepository, times(1)).findByShortUrl(shortUrl);
    }

    @Test
    void getOriginalUrl_WhenUrlDoesNotExist_ShouldReturnEmpty() {
        // Arrange
        when(urlRepository.findByShortUrl("nonexistent")).thenReturn(Optional.empty());

        // Act
        Optional<Url> result = urlService.getOriginalUrl("nonexistent");

        // Assert
        assertFalse(result.isPresent());
        verify(urlRepository, times(1)).findByShortUrl("nonexistent");
    }
}