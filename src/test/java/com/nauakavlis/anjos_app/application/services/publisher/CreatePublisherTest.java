package com.nauakavlis.anjos_app.application.services.publisher;

import com.nauakavlis.anjos_app.application.ports.publisher.input.CreatePublisher.CreatePublisherInput;
import com.nauakavlis.anjos_app.application.ports.publisher.input.CreatePublisher.CreatePublisherOutput;
import com.nauakavlis.anjos_app.application.ports.publisher.output.PublisherRepository;
import com.nauakavlis.anjos_app.domain.model.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreatePublisherTest {

    private PublisherRepository publisherRepository;
    private CreatePublisher createPublisher;

    @BeforeEach
    void setUp() {
        publisherRepository = mock(PublisherRepository.class);
        createPublisher = new CreatePublisher(publisherRepository);
    }

    @Nested
    @DisplayName("Success Paths")
    class SuccessPaths {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = { "Test Create Publisher Description" })
        @DisplayName("It should create a publisher with or without description")
        void it_should_create_a_publisher_with_or_without_description(String publisherDescription) {

            String publisherName = "Test Create Publisher";
            CreatePublisherInput input = new CreatePublisherInput(publisherName, publisherDescription);
            Publisher savedPublisher = new Publisher(1L, publisherName, publisherDescription);

            when(publisherRepository.save(any(Publisher.class))).thenReturn(savedPublisher);

            CreatePublisherOutput output = createPublisher.execute(input);

            assertEquals(1L, output.id());
            assertEquals(publisherName, output.name());
            assertEquals(publisherDescription, output.description());
        }

        @Test
        @DisplayName("It should call repository one time")
        void it_should_call_repository_one_time() {
            CreatePublisherInput input = new CreatePublisherInput("X", "Y");
            when(publisherRepository.save(any(Publisher.class))).thenReturn(new Publisher(1L, "X", "Y"));

            createPublisher.execute(input);

            verify(publisherRepository, times(1)).save(any(Publisher.class));
            verifyNoMoreInteractions(publisherRepository);
        }
    }

    @Nested
    @DisplayName("Fail Paths")
    class FailPaths {
        @Test
        @DisplayName("It should propagate the exception when the repository fails to save")
        void it_should_propagate_exception_when_repository_fails() {
            CreatePublisherInput input = new CreatePublisherInput("Qualquer", "Qualquer");
            when(publisherRepository.save(any(Publisher.class)))
                    .thenThrow(new RuntimeException("Falha ao acessar o banco de dados"));

            RuntimeException exception = assertThrows(RuntimeException.class,
                    () -> createPublisher.execute(input));
            assertEquals("Falha ao acessar o banco de dados", exception.getMessage());
        }
    }
}