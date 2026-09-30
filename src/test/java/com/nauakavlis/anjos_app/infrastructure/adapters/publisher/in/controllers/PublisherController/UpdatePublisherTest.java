package com.nauakavlis.anjos_app.infrastructure.adapters.publisher.in.controllers.PublisherController;

import com.nauakavlis.anjos_app.application.ports.publisher.in.CreatePublisher.CreatePublisherUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.in.DeletePublisher.DeletePublisherUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.in.GetAllPublishers.GetAllPublishersUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.in.GetPublisherById.GetPublisherByIdUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.in.UpdatePublisher.UpdatePublisherInput;
import com.nauakavlis.anjos_app.application.ports.publisher.in.UpdatePublisher.UpdatePublisherOutput;
import com.nauakavlis.anjos_app.application.ports.publisher.in.UpdatePublisher.UpdatePublisherUseCase;
import com.nauakavlis.anjos_app.domain.exception.publisher.PublisherNotFoundException;
import com.nauakavlis.anjos_app.infrastructure.adapters.publisher.in.PublisherController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.graphql.test.autoconfigure.GraphQlTest;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@GraphQlTest(PublisherController.class)
public class UpdatePublisherTest {

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
        @DisplayName("It should update a publisher")
        void it_should_update_a_publisher() {
            Long publisherId = 1L;
            String publisherName = "Updated Publisher";
            String publisherDescription = "Updated Description";

            UpdatePublisherOutput mockOutput = new UpdatePublisherOutput(publisherId, publisherName, publisherDescription);
            when(updatePublisherUseCase.execute(eq(publisherId), any(UpdatePublisherInput.class)))
                    .thenReturn(mockOutput);

            Map<String, Object> graphqlInputMap = new HashMap<>();
            graphqlInputMap.put("name", publisherName);
            graphqlInputMap.put("description", publisherDescription);

            graphQlTester.documentName("updatePublisher")
                    .variable("id", publisherId)
                    .variable("input", graphqlInputMap)
                    .execute()
                    .path("updatePublisher")
                    .entity(UpdatePublisherOutput.class)
                    .satisfies(publisher -> {
                        assertEquals(publisherId, publisher.id());
                        assertEquals(publisherName, publisher.name());
                        assertEquals(publisherDescription, publisher.description());
                    });
        }
    }

    @Nested
    @DisplayName("Failure Paths")
    class FailurePaths {

        @Test
        @DisplayName("It should fail when publisher is not found")
        void it_should_fail_when_publisher_is_not_found () {
            Long publisherId = 1L;
            String publisherName = "Updated Publisher";
            String publisherDescription = "Updated Description";

            when(updatePublisherUseCase.execute(eq(publisherId), any(UpdatePublisherInput.class)))
                    .thenThrow(new PublisherNotFoundException("Publisher not found with id: " + publisherId));

            Map<String, Object> graphqlInputMap = new HashMap<>();
            graphqlInputMap.put("name", publisherName);
            graphqlInputMap.put("description", publisherDescription);

            graphQlTester.documentName("updatePublisher")
                    .variable("id", publisherId)
                    .variable("input", graphqlInputMap)
                    .execute()
                    .errors()
                    .expect(responseError ->
                            ErrorType.NOT_FOUND.equals(responseError.getErrorType())
                                    && Objects.requireNonNull(responseError.getMessage()).contains("Publisher not found with id: " + publisherId))
                    .verify();
        }
    }

    @Nested
    @DisplayName("Boundary Tests")
    class BoundaryTests {

        @Nested
        @DisplayName("Name Scenarios")
        class NameScenarios {

            @ParameterizedTest
            @NullAndEmptySource
            @ValueSource(strings = {"   ", })
            @DisplayName("It should fail when name is blank")
            void it_should_fail_when_name_is_blank(String publisherName) {
                Long publisherId = 1L;
                String publisherDescription = "descrition placehold";

                Map<String, Object> graphqlInputMap = new HashMap<>();
                graphqlInputMap.put("name", publisherName);
                graphqlInputMap.put("description", publisherDescription);

                graphQlTester.documentName("updatePublisher")
                        .variable("id", publisherId)
                        .variable("input", graphqlInputMap)
                        .execute()
                        .errors()
                        .expect(responseError -> true)
                        .verify();

                verify(updatePublisherUseCase, never()).execute(any(), any());
            }

            @Test
            @DisplayName("It should fail when name exceeds 100 characters")
            void it_should_fail_when_name_exceeds_100_characters () {
                String publisherName = "N".repeat(101);
                String publisherDescription = "descrition placehold";

                Map<String, Object> graphqlInputMap = new HashMap<>();
                graphqlInputMap.put("name", publisherName);
                graphqlInputMap.put("description", publisherDescription);

                graphQlTester.documentName("updatePublisher")
                        .variable("input", graphqlInputMap)
                        .execute()
                        .errors()
                        .expect(responseError -> true)
                        .verify();

                verify(updatePublisherUseCase, never()).execute(any(), any());
            }
        }

        @Nested
        @DisplayName("Description Scenarios")
        class DescriptionScenarios {

            @Test
            @DisplayName("It should fail when description exceeds 255 characters")
            void it_should_fail_when_description_exceeds_255_characters () {
                String publisherName = "publisher placeholder";
                String publisherDescription = "D".repeat(256);

                Map<String, Object> graphqlInputMap = new HashMap<>();
                graphqlInputMap.put("name", publisherName);
                graphqlInputMap.put("description", publisherDescription);

                graphQlTester.documentName("updatePublisher")
                        .variable("input", graphqlInputMap)
                        .execute()
                        .errors()
                        .expect(responseError -> true)
                        .verify();

                verify(updatePublisherUseCase, never()).execute(any(), any());
            }
        }
    }
}