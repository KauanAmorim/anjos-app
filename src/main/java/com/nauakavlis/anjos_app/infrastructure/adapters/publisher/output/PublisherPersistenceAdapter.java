package com.nauakavlis.anjos_app.infrastructure.adapters.publisher.out;

import com.nauakavlis.anjos_app.application.ports.publisher.out.PublisherRepository;
import com.nauakavlis.anjos_app.domain.model.Author;
import com.nauakavlis.anjos_app.domain.model.Book;
import com.nauakavlis.anjos_app.domain.model.Publisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PublisherPersistenceAdapter implements PublisherRepository {

    private final PublisherJpaRepository publisherJpaRepository;

    public PublisherPersistenceAdapter(PublisherJpaRepository publisherJpaRepository) {
        this.publisherJpaRepository = publisherJpaRepository;
    }

    @Override
    public Publisher save(Publisher publisher) {

        PublisherEntity publisherEntity = new PublisherEntity();
        publisherEntity.setName(publisher.name());
        publisherEntity.setDescription(publisher.description());

        PublisherEntity savedPublisherEntity = publisherJpaRepository.save(publisherEntity);

        return new Publisher(
                savedPublisherEntity.getId(),
                savedPublisherEntity.getName(),
                savedPublisherEntity.getDescription()
        );
    }

    @Override
    public boolean deleteById(Long id) {
        if (publisherJpaRepository.existsById(id)) {
            publisherJpaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Publisher> findById(Long id) {
        Optional<PublisherEntity> publisherEntity = publisherJpaRepository.findById(id);
        return publisherEntity.map(entity -> new Publisher(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        ));

    }

    @Override
    public List<Publisher> findAll() {

        List<PublisherEntity> publisherEntities = publisherJpaRepository.findAll();

        return publisherEntities.stream().map(publisherEntity -> new Publisher(
                publisherEntity.getId(),
                publisherEntity.getName(),
                publisherEntity.getDescription()
        )).collect(Collectors.toList());
    }

    @Override
    public List<Book> findBooksByPublisherId(Long publisherId) {
        return List.of();
    }

    @Override
    public List<Author> findAuthorsByPublisherId(Long publisherId) {
        return List.of();
    }
}
