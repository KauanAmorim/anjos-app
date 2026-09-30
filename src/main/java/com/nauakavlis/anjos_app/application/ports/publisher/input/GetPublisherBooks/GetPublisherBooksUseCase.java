package com.nauakavlis.anjos_app.application.ports.publisher.input.GetPublisherBooks;

import java.util.List;

public interface GetPublisherBooksUseCase {
    List<GetPublisherBooksOutput> execute(Long id);
}