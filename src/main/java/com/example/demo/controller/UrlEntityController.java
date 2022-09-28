package com.example.demo.controller;

import com.example.demo.DTO.UrlDTO;
import com.example.demo.repository.UrlEntityRepository;
import com.example.demo.service.UrlService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/urls")
public class UrlEntityController {

    private UrlService urlService;

    @PostMapping("/short")
    public ResponseEntity<Object> generateShort(@RequestBody UrlDTO dto) {
        String shortUrl = urlService.generateShortUrl(dto);
        URI redirectUrl = buildRedirectUrl(shortUrl);
        return ResponseEntity.created(redirectUrl).build();
    }

    @GetMapping("/short/{shortenUrlId}")
    public ResponseEntity<Object> redirecting(@PathVariable String shortenUrlId) {
        Optional<String> originalUrl = urlService.findByShortUrl(shortenUrlId);
        if (originalUrl.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT)
                .location(URI.create(originalUrl.get()))
                .build();
    }

    private static URI buildRedirectUrl(String shortenedUrl) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .pathSegment("{shortenedUrlId}")
                .buildAndExpand(shortenedUrl)
                .toUri();
    }
}
