package com.nauakavlis.anjos_app.application.services.publisher;

import com.nauakavlis.anjos_app.application.ports.publisher.input.GetPublisherBooks.GetPublisherBooksOutput;
import com.nauakavlis.anjos_app.application.ports.publisher.input.GetPublisherBooks.GetPublisherBooksUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPublisherBooks implements GetPublisherBooksUseCase {

    @Override
    public List<GetPublisherBooksOutput> execute(Long id) {
        return List.of(new GetPublisherBooksOutput());
    }
}
