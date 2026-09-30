package com.nauakavlis.anjos_app.application.services.publisher;

import com.nauakavlis.anjos_app.application.ports.publisher.input.GetPublisherById.GetPublisherByIdOutput;
import com.nauakavlis.anjos_app.application.ports.publisher.input.GetPublisherById.GetPublisherByIdUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.output.PublisherRepository;
import com.nauakavlis.anjos_app.domain.exception.publisher.PublisherNotFoundException;
import com.nauakavlis.anjos_app.domain.model.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetPublisherById implements GetPublisherByIdUseCase {

    private final PublisherRepository publisherRepository;

    @Override
    public GetPublisherByIdOutput execute(Long id) {
        Optional<Publisher> optionalPublisher = publisherRepository.findById(id);

        optionalPublisher.orElseThrow(() -> new PublisherNotFoundException("Publisher not found with id: " + id));
        Publisher publisher = optionalPublisher.get();

        return new GetPublisherByIdOutput(
                publisher.id(),
                publisher.name(),
                publisher.description()
        );
    }
}
