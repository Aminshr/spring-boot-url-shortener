package org.example.urlshortner.service;

import org.example.urlshortner.model.Url;
import org.example.urlshortner.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    
    @Autowired
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }
    
    public Url generateShortUrl(String originalUrl) {
        // Check if URL already exists in database
        // For simplicity, we're not checking this in this example
        
        // Generate a random short URL
        String shortUrl = generateRandomString(6);
        
        // Create and save the new URL
        Url url = new Url(originalUrl, shortUrl);
        return urlRepository.save(url);
    }
    
    public Optional<Url> getOriginalUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl);
    }
    
    private String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        
        return sb.toString();
    }
}