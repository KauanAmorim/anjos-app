package com.nauakavlis.anjos_app.application.ports.publisher.input.GetPublisherAuthors;

import java.util.List;

public interface GetPublisherAuthorsUseCase {
    List<GetPublisherAuthorsOutput> execute(Long id);
}