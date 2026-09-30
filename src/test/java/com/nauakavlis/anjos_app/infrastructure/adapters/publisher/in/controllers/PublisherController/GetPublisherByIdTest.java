package com.nauakavlis.anjos_app.infrastructure.adapters.publisher.in.controllers.PublisherController;

import com.nauakavlis.anjos_app.application.ports.publisher.in.CreatePublisher.CreatePublisherUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.in.DeletePublisher.DeletePublisherUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.in.GetAllPublishers.GetAllPublishersUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.in.GetPublisherById.GetPublisherByIdOutput;
import com.nauakavlis.anjos_app.application.ports.publisher.in.GetPublisherById.GetPublisherByIdUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.in.UpdatePublisher.UpdatePublisherUseCase;
import com.nauakavlis.anjos_app.domain.exception.publisher.PublisherNotFoundException;
import com.nauakavlis.anjos_app.infrastructure.adapters.publisher.in.PublisherController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.graphql.test.autoconfigure.GraphQlTest;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@GraphQlTest(PublisherController.class)
public class GetPublisherByIdTest {

    @Autowired
    protected GraphQlTester graphQlTester;

    // Commands
    @MockitoBean
    protected CreatePublisherUseCase createPublisherUseCase;
    @MockitoBean
    protected UpdatePublisherUseCase updatePublisherUseCase;
    @MockitoBean
    protected DeletePublisherUseCase deletePublisherUseCase;

    // Queries
    @MockitoBean
    protected GetPublisherByIdUseCase getPublisherByIdUseCase;
    @MockitoBean
    protected GetAllPublishersUseCase getAllPublishersUseCase;

    @Nested
    @DisplayName("Success Paths")
    class SuccessPaths {

        @Test
        @DisplayName("It should get a publisher by ID")
        void it_should_get_a_publisher_by_id() {
            Long publisherId = 1L;
            String name = "Test Publisher";
            String description = "Description";

            GetPublisherByIdOutput mockOutput = new GetPublisherByIdOutput(publisherId, name, description);
            when(getPublisherByIdUseCase.execute(publisherId)).thenReturn(mockOutput);

            graphQlTester.documentName("getPublisherById")
                    .variable("id", publisherId)
                    .execute()
                    .path("getPublisherById")
                    .entity(GetPublisherByIdOutput.class)
                    .satisfies(publisher -> {
                        assertEquals(publisherId, publisher.id());
                        assertEquals(name, publisher.name());
                        assertEquals(description, publisher.description());
                    });
        }
    }

    @Nested
    @DisplayName("Failure Paths")
    class FailurePaths {

        @Test
        @DisplayName("It should not get a publisher by ID if it does not exist")
        void it_should_not_get_a_publisher_by_id_if_it_does_not_exist() {
            Long publisherId = 1L;

            when(getPublisherByIdUseCase.execute(publisherId))
                    .thenThrow(new PublisherNotFoundException("Publisher not found with id: " + publisherId));

            graphQlTester.documentName("getPublisherById")
                    .variable("id", publisherId)
                    .execute()
                    .errors()
                    .expect(responseError ->
                            ErrorType.NOT_FOUND.equals(responseError.getErrorType())
                                    && Objects.requireNonNull(responseError.getMessage()).contains("Publisher not found with id: " + publisherId))
                    .verify();
        }
    }
}