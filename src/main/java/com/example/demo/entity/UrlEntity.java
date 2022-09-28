package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "shortened_urls")
@Data
public class UrlEntity {

    @Id
    private String id;

    @Column(name = "original_url", nullable = false, unique = true)
    private String originalUrl;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
