package com.nauakavlis.anjos_app.infrastructure.adapters.publisher.in.controllers.PublisherController;

import com.nauakavlis.anjos_app.application.ports.publisher.in.CreatePublisher.CreatePublisherUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.in.DeletePublisher.DeletePublisherUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.in.GetAllPublishers.GetAllPublishersOutput;
import com.nauakavlis.anjos_app.application.ports.publisher.in.GetAllPublishers.GetAllPublishersUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.in.GetPublisherById.GetPublisherByIdUseCase;
import com.nauakavlis.anjos_app.application.ports.publisher.in.UpdatePublisher.UpdatePublisherUseCase;
import com.nauakavlis.anjos_app.infrastructure.adapters.publisher.in.PublisherController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.graphql.test.autoconfigure.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@GraphQlTest(PublisherController.class)
public class GetAllPublishersTest {

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
        @DisplayName("It should get all publishers")
        void it_should_get_all_publishers() {
            GetAllPublishersOutput pub1 = new GetAllPublishersOutput(1L, "Pub 1", "");
            GetAllPublishersOutput pub2 = new GetAllPublishersOutput(2L, "Pub 2", "Desc 2");

            when(getAllPublishersUseCase.execute()).thenReturn(List.of(pub1, pub2));

            graphQlTester.documentName("getAllPublishers")
                    .execute()
                    .path("getAllPublishers")
                    .entityList(GetAllPublishersOutput.class)
                    .hasSize(2)
                    .satisfies(publishers -> {
                        assertEquals(1L, publishers.get(0).id());
                        assertEquals("Pub 1", publishers.get(0).name());
                        assertEquals("", publishers.get(0).description());
                        assertEquals(2L, publishers.get(1).id());
                        assertEquals("Pub 2", publishers.get(1).name());
                        assertEquals("Desc 2", publishers.get(1).description());
                    });
        }
    }
}