package com.nauakavlis.anjos_app.domain.exception.publisher;

import com.nauakavlis.anjos_app.domain.exception.NotFoundException;

public class PublisherNotFoundException extends NotFoundException {
    public PublisherNotFoundException(String message) {
        super(message);
    }
}
