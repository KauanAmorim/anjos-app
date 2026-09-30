package com.nauakavlis.anjos_app.infrastructure.adapters.publisher.in.controllers.PublisherController;

import com.nauakavlis.anjos_app.application.ports.publisher.in.CreatePublisher.CreatePublisherUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.in.DeletePublisher.DeletePublisherUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.in.GetAllPublishers.GetAllPublishersUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.in.GetPublisherById.GetPublisherByIdUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.in.UpdatePublisher.UpdatePublisherUseCase;
import com.nauakavlis.anjos_app.infrastructure.adapters.publisher.in.PublisherController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.graphql.test.autoconfigure.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@GraphQlTest(PublisherController.class)
public class DeletePublisherTest {

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
        @DisplayName("It should delete a publisher")
        void it_should_delete_a_publisher() {
            Long publisherId = 1L;

            when(deletePublisherUseCase.execute(publisherId)).thenReturn(true);

            graphQlTester.documentName("deletePublisher")
                    .variable("id", publisherId)
                    .execute()
                    .path("deletePublisher")
                    .entity(Boolean.class)
                    .satisfies(Assertions::assertTrue);
        }
    }

    @Nested
    @DisplayName("Failure Paths")
    class FailurePaths {

        @Test
        @DisplayName("It should not delete a publisher if the ID is invalid")
        void it_should_not_delete_a_publisher_if_the_ID_is_invalid() {
            Long publisherId = 1L;

            when(deletePublisherUseCase.execute(publisherId)).thenReturn(false);

            graphQlTester.documentName("deletePublisher")
                    .variable("id", publisherId)
                    .execute()
                    .path("deletePublisher")
                    .entity(Boolean.class)
                    .satisfies(Assertions::assertFalse);
        }
    }
}