package com.nauakavlis.anjos_app.application.services.publisher;

import com.nauakavlis.anjos_app.application.ports.publisher.input.UpdatePublisher.UpdatePublisherInput;
import com.nauakavlis.anjos_app.application.ports.publisher.input.UpdatePublisher.UpdatePublisherOutput;
import com.nauakavlis.anjos_app.application.ports.publisher.input.UpdatePublisher.UpdatePublisherUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.output.PublisherRepository;
import com.nauakavlis.anjos_app.domain.model.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdatePublisher implements UpdatePublisherUseCase {

    private final PublisherRepository publisherRepository;

    @Override
    public UpdatePublisherOutput execute(Long id, UpdatePublisherInput input) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publisher not found with id: " + id));


        Publisher updatedPublisher = publisherRepository.save(new Publisher(
                publisher.id(),
                input.name(),
                input.description()
        ));

        return new UpdatePublisherOutput(
                updatedPublisher.id(),
                updatedPublisher.name(),
                updatedPublisher.description()
        );
    }

}
