package com.nauakavlis.anjos_app.infrastructure.adapters.publisher.in;

import java.util.List;

import com.nauakavlis.anjos_app.application.ports.publisher.input.CreatePublisher.CreatePublisherInput;
import com.nauakavlis.anjos_app.application.ports.publisher.input.CreatePublisher.CreatePublisherOutput;
import com.nauakavlis.anjos_app.application.ports.publisher.input.CreatePublisher.CreatePublisherUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.input.DeletePublisher.DeletePublisherUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.input.GetPublisherById.GetPublisherByIdUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.input.UpdatePublisher.UpdatePublisherInput;
import com.nauakavlis.anjos_app.application.ports.publisher.input.UpdatePublisher.UpdatePublisherOutput;
import com.nauakavlis.anjos_app.application.ports.publisher.input.UpdatePublisher.UpdatePublisherUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.nauakavlis.anjos_app.application.ports.publisher.input.GetAllPublishers.GetAllPublishersOutput;
import com.nauakavlis.anjos_app.application.ports.publisher.input.GetAllPublishers.GetAllPublishersUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.input.GetPublisherById.GetPublisherByIdOutput;

@Controller
@RequiredArgsConstructor
public class PublisherController {

    // Commands
    private final CreatePublisherUseCase createPublisherUseCase;
    private final UpdatePublisherUseCase updatePublisherUseCase;
    private final DeletePublisherUseCase deletePublisherUseCase;

    // Querys
    private final GetPublisherByIdUseCase getPublisherByIdUseCase;
    private final GetAllPublishersUseCase getAllPublishersUseCase;


    @MutationMapping
    public CreatePublisherOutput createPublisher(@Valid @Argument CreatePublisherInput input) {
        return createPublisherUseCase.execute(input);
    }

    @MutationMapping
    public UpdatePublisherOutput updatePublisher(@Argument Long id, @Valid @Argument UpdatePublisherInput input) {
        return updatePublisherUseCase.execute(id, input);
    }

    @MutationMapping
    public boolean deletePublisher(@Argument Long id) {
        return deletePublisherUseCase.execute(id);
    }

    @QueryMapping
    public GetPublisherByIdOutput getPublisherById(@Argument Long id) {
        return getPublisherByIdUseCase.execute(id);
    }

    @QueryMapping
    public List<GetAllPublishersOutput> getAllPublishers() {
        return getAllPublishersUseCase.execute();
    }
}