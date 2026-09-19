package com.NauakAvlis.anjos_app.infrastructure.adapters.publisher.in;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.NauakAvlis.anjos_app.application.ports.publisher.in.GetAllPublishers.GetAllPublishersOutPut;
import com.NauakAvlis.anjos_app.application.ports.publisher.in.GetAllPublishers.GetAllPublishersUseCase;
import com.NauakAvlis.anjos_app.application.ports.publisher.in.GetPublisher.GetPublisherOutPut;
import com.NauakAvlis.anjos_app.application.ports.publisher.in.GetPublisher.GetPublisherUseCase;
import com.NauakAvlis.anjos_app.application.ports.publisher.in.GetPublisherAuthors.GetPublisherAuthorsOutPut;
import com.NauakAvlis.anjos_app.application.ports.publisher.in.GetPublisherAuthors.GetPublisherAuthorsUseCase;
import com.NauakAvlis.anjos_app.application.ports.publisher.in.GetPublisherBooks.GetPublisherBooksOutPut;
import com.NauakAvlis.anjos_app.application.ports.publisher.in.GetPublisherBooks.GetPublisherBooksUseCase;

@Controller
public class PublisherController {

    private final GetPublisherUseCase getPublisherUseCase;
    private final GetAllPublishersUseCase getAllPublishersUseCase;
    private final GetPublisherAuthorsUseCase getPublisherAuthorsUseCase;
    private final GetPublisherBooksUseCase getPublisherBooksUseCase;

    public PublisherController(
      GetPublisherUseCase getPublisherUseCase,
      GetAllPublishersUseCase getAllPublishersUseCase,
      GetPublisherAuthorsUseCase getPublisherAuthorsUseCase,
      GetPublisherBooksUseCase getPublisherBooksUseCase
    ) {
        this.getPublisherUseCase = getPublisherUseCase;
        this.getAllPublishersUseCase = getAllPublishersUseCase;
        this.getPublisherAuthorsUseCase = getPublisherAuthorsUseCase;
        this.getPublisherBooksUseCase = getPublisherBooksUseCase;
    }

    @QueryMapping
    public GetPublisherOutPut getPublisherById(@Argument Long id) {
        return getPublisherUseCase.execute(id);
    }

    @QueryMapping
    public List<GetAllPublishersOutPut> getAllPublishers() {
        return getAllPublishersUseCase.execute();
    }

    @SchemaMapping
    public GetPublisherAuthorsOutPut getPublisherAuthors(@Argument Long id) {
        return getPublisherAuthorsUseCase.execute(id);
    }


    @SchemaMapping
    public GetPublisherBooksOutPut getPublisherBooks(@Argument Long id) {
        return getPublisherBooksUseCase.execute(id);
    }
}