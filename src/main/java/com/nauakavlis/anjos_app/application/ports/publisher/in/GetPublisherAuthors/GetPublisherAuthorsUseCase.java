package com.nauakavlis.anjos_app.application.ports.publisher.in.GetPublisherAuthors;

import java.util.List;

public interface GetPublisherAuthorsUseCase {
    List<GetPublisherAuthorsOutput> execute(Long id);
}