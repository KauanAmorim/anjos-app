package com.NauakAvlis.anjos_app.domain.service.publisher;

import com.NauakAvlis.anjos_app.application.ports.publisher.in.GetPublisherBooks.GetPublisherBooksOutPut;
import com.NauakAvlis.anjos_app.application.ports.publisher.in.GetPublisherBooks.GetPublisherBooksUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetPublisherBooks implements GetPublisherBooksUseCase {

    @Override
    public GetPublisherBooksOutPut execute(Long id) {
        return new GetPublisherBooksOutPut();
    }
}
