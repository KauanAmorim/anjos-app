package com.nauakavlis.anjos_app.application.services.publisher;

import com.nauakavlis.anjos_app.application.ports.publisher.input.GetPublisherAuthors.GetPublisherAuthorsOutput;
import com.nauakavlis.anjos_app.application.ports.publisher.input.GetPublisherAuthors.GetPublisherAuthorsUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPublisherAuthors implements GetPublisherAuthorsUseCase {

    @Override
    public List<GetPublisherAuthorsOutput> execute(Long id) {
        return List.of(new GetPublisherAuthorsOutput());
    }
}
