package com.nauakavlis.anjos_app.domain.model;

public record Book(Long id, String title, String description, Author author, Publisher publisher) { }
