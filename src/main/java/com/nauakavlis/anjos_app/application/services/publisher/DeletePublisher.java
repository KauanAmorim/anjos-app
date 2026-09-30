package com.nauakavlis.anjos_app.application.services.publisher;

import com.nauakavlis.anjos_app.application.ports.publisher.input.DeletePublisher.DeletePublisherUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.output.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePublisher implements DeletePublisherUseCase {

    private final PublisherRepository publisherRepository;

    public boolean execute(Long id) {
        return publisherRepository.deleteById(id);
    }
}
