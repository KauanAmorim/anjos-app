package com.NauakAvlis.anjos_app.domain.service.publisher;

import com.NauakAvlis.anjos_app.application.ports.publisher.in.GetPublisher.GetPublisherOutPut;
import com.NauakAvlis.anjos_app.application.ports.publisher.in.GetPublisher.GetPublisherUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetPublisher implements GetPublisherUseCase {

    @Override
    public GetPublisherOutPut execute(Long id) {
        return new GetPublisherOutPut();
    }
}
