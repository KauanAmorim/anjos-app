package com.NauakAvlis.anjos_app.domain.service.publisher;

import com.NauakAvlis.anjos_app.application.ports.publisher.in.GetPublisherAuthors.GetPublisherAuthorsOutPut;
import com.NauakAvlis.anjos_app.application.ports.publisher.in.GetPublisherAuthors.GetPublisherAuthorsUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetPublisherAuthors implements GetPublisherAuthorsUseCase {

    @Override
    public GetPublisherAuthorsOutPut execute(Long id) {
        return new GetPublisherAuthorsOutPut();
    }
}
