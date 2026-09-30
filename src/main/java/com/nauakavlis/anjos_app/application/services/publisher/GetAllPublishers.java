package com.nauakavlis.anjos_app.application.services.publisher;

import com.nauakavlis.anjos_app.application.ports.publisher.input.GetAllPublishers.GetAllPublishersOutput;
import com.nauakavlis.anjos_app.application.ports.publisher.input.GetAllPublishers.GetAllPublishersUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.output.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllPublishers implements GetAllPublishersUseCase {

    private final PublisherRepository publisherRepository;

    @Override
    public List<GetAllPublishersOutput> execute() {
        return publisherRepository.findAll()
                .stream()
                .map(publisher -> new GetAllPublishersOutput(
                    publisher.id(),
                    publisher.name(),
                    publisher.description()
                )).toList();
    }
}
