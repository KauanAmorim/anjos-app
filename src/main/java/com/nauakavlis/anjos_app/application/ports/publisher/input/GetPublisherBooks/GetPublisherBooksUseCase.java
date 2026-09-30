package com.nauakavlis.anjos_app.application.ports.publisher.in.GetPublisherBooks;

import java.util.List;

public interface GetPublisherBooksUseCase {
    List<GetPublisherBooksOutput> execute(Long id);
}