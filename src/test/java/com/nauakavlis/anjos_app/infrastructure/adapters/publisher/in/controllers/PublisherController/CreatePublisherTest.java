package com.nauakavlis.anjos_app.infrastructure.adapters.publisher.in.controllers.PublisherController;

import com.nauakavlis.anjos_app.application.ports.publisher.in.CreatePublisher.CreatePublisherInput;
import com.nauakavlis.anjos_app.application.ports.publisher.in.CreatePublisher.CreatePublisherOutput;
import com.nauakavlis.anjos_app.application.ports.publisher.in.CreatePublisher.CreatePublisherUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.in.DeletePublisher.DeletePublisherUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.in.GetAllPublishers.GetAllPublishersUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.in.GetPublisherById.GetPublisherByIdUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.in.UpdatePublisher.UpdatePublisherUseCase;
import com.nauakavlis.anjos_app.infrastructure.adapters.publisher.in.PublisherController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.graphql.test.autoconfigure.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@GraphQlTest(PublisherController.class)
public class CreatePublisherTest {

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

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = { "Test Create Publisher Description" })
        @DisplayName("It should create a publisher with or without description")
        void it_should_create_a_publisher_with_or_without_description(String publisherDescription) {
            String publisherName = "Test Create Publisher";

            CreatePublisherOutput mockOutput = new CreatePublisherOutput(1L, publisherName, publisherDescription);
            when(createPublisherUseCase.execute(any(CreatePublisherInput.class))).thenReturn(mockOutput);

            Map<String, Object> graphqlInputMap = new HashMap<>();
            graphqlInputMap.put("name", publisherName);
            graphqlInputMap.put("description", publisherDescription);

            graphQlTester.documentName("createPublisher")
                    .variable("input", graphqlInputMap)
                    .execute()
                    .path("createPublisher")
                    .entity(CreatePublisherOutput.class)
                    .satisfies(publisher -> {
                        assertNotNull(publisher.id());
                        assertEquals(publisherName, publisher.name());
                        assertEquals(publisherDescription, publisher.description());
                    });
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
                String publisherDescription = "descrition placehold";

                Map<String, Object> graphqlInputMap = new HashMap<>();
                graphqlInputMap.put("name", publisherName);
                graphqlInputMap.put("description", publisherDescription);

                graphQlTester.documentName("createPublisher")
                        .variable("input", graphqlInputMap)
                        .execute()
                        .errors()
                        .expect(responseError -> true)
                        .verify();

                verify(createPublisherUseCase, never()).execute(any());

            }

            @Test
            @DisplayName("It should fail when name exceeds 100 characters")
            void it_should_fail_when_name_exceeds_100_characters () {
                String publisherName = "N".repeat(101);
                String publisherDescription = "descrition placehold";

                Map<String, Object> graphqlInputMap = new HashMap<>();
                graphqlInputMap.put("name", publisherName);
                graphqlInputMap.put("description", publisherDescription);

                graphQlTester.documentName("createPublisher")
                        .variable("input", graphqlInputMap)
                        .execute()
                        .errors()
                        .expect(responseError -> true)
                        .verify();

                verify(createPublisherUseCase, never()).execute(any());
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

                graphQlTester.documentName("createPublisher")
                        .variable("input", graphqlInputMap)
                        .execute()
                        .errors()
                        .expect(responseError -> true)
                        .verify();

                verify(createPublisherUseCase, never()).execute(any());
            }
        }

    }

}
