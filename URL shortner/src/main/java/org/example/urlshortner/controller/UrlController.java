package org.example.urlshortner.controller;

import org.example.urlshortner.model.Url;
import org.example.urlshortner.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UrlController {

    private final UrlService urlService;
    
    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }
    
    @GetMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("url", new Url());
        return "index";
    }
    
    @PostMapping("/shorten")
    public String shortenUrl(@ModelAttribute Url url, Model model) {
        Url shortenedUrl = urlService.generateShortUrl(url.getOriginalUrl());
        model.addAttribute("url", shortenedUrl);
        model.addAttribute("host", "http://localhost:8080/");
        return "result";
    }
    
    @GetMapping("/{shortUrl}")
    public String redirectToOriginalUrl(@PathVariable String shortUrl) {
        Optional<Url> url = urlService.getOriginalUrl(shortUrl);
        
        if (url.isPresent()) {
            return "redirect:" + url.get().getOriginalUrl();
        } else {
            return "redirect:/";
        }
    }
}