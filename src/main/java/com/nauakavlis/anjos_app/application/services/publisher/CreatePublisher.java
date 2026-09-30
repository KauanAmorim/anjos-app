package com.nauakavlis.anjos_app.application.services.publisher;

import com.nauakavlis.anjos_app.application.ports.publisher.in.CreatePublisher.CreatePublisherInput;
import com.nauakavlis.anjos_app.application.ports.publisher.in.CreatePublisher.CreatePublisherOutput;
import com.nauakavlis.anjos_app.application.ports.publisher.in.CreatePublisher.CreatePublisherUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.out.PublisherRepository;
import com.nauakavlis.anjos_app.domain.model.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePublisher implements CreatePublisherUseCase {

    private final PublisherRepository publisherRepository;

    @Override
    public CreatePublisherOutput execute(CreatePublisherInput input) {
        Publisher newPublisher = new Publisher(
                null,
                input.name(),
                input.description()
        );

        Publisher savedPublisher = publisherRepository.save(newPublisher);
        return new CreatePublisherOutput(
                savedPublisher.id(),
                savedPublisher.name(),
                savedPublisher.description()
        );
    }
}
