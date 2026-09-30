package com.nauakavlis.anjos_app.application.ports.publisher.out;

import com.nauakavlis.anjos_app.domain.model.Author;
import com.nauakavlis.anjos_app.domain.model.Book;
import com.nauakavlis.anjos_app.domain.model.Publisher;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository {

    public Publisher save(Publisher publisher);
    public boolean deleteById(Long id);
    public Optional<Publisher> findById(Long id);
    public List<Publisher> findAll();
    public List<Book> findBooksByPublisherId(Long publisherId);
    public List<Author> findAuthorsByPublisherId(Long publisherId);
}
