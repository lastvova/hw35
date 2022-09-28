package com.example.demo.service;

import com.example.demo.DTO.UrlDTO;
import com.example.demo.entity.UrlEntity;
import com.example.demo.repository.UrlEntityRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UrlService {

    public static final int SHORTENED_URL_LENGTH = 7;
    private UrlEntityRepository repository;

    @Transactional
    @Retryable
    public String generateShortUrl(UrlDTO dto) {
        final UrlEntity url = new UrlEntity();

        final String urlId = RandomStringUtils.random(SHORTENED_URL_LENGTH, true, false);
        url.setId(urlId);
        url.setOriginalUrl(dto.url());
        url.setTitle(dto.title());

        return repository.save(url).getId();
    }

    @Cacheable("urls")
    public Optional<String> findByShortUrl(String shortUrl) {
        return repository.findById(shortUrl).map(UrlEntity::getOriginalUrl);
    }
}
