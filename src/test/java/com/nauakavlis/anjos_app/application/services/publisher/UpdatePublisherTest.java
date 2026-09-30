package com.nauakavlis.anjos_app.application.services.publisher;

import com.nauakavlis.anjos_app.application.ports.publisher.input.UpdatePublisher.UpdatePublisherInput;
import com.nauakavlis.anjos_app.application.ports.publisher.input.UpdatePublisher.UpdatePublisherOutput;
import com.nauakavlis.anjos_app.application.ports.publisher.output.PublisherRepository;
import com.nauakavlis.anjos_app.domain.exception.publisher.PublisherNotFoundException;
import com.nauakavlis.anjos_app.domain.model.Publisher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class UpdatePublisherTest {

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private UpdatePublisher updatePublisher;

    @Nested
    @DisplayName("Success Paths")
    class SuccessPaths {

        @Test
        @DisplayName("It should update publisher")
        void it_should_update_publisher() {
            Long publisherId = 1L;
            String publisherName = "name placeholder";
            String publisherDescription = "description placeholder";
            UpdatePublisherInput updatePublisherInput = new UpdatePublisherInput(publisherName, publisherDescription);
            Optional<Publisher> optionalPublisherFounded = Optional.of(new Publisher(publisherId, publisherName, publisherDescription));
            Publisher publisherToUpdate = new Publisher(publisherId, publisherName, publisherDescription);
            Publisher publisherUpdated = new Publisher(publisherId, publisherName, publisherDescription);

            when(publisherRepository.findById(publisherId)).thenReturn(optionalPublisherFounded);
            when(publisherRepository.save(publisherToUpdate)).thenReturn(publisherUpdated);

            UpdatePublisherOutput result = updatePublisher.execute(publisherId, updatePublisherInput);

            assertEquals(publisherName, result.name());
            assertEquals(publisherDescription, result.description());
        }

        @Test
        @DisplayName("It should call repository one time")
        void it_should_call_repository_one_time() {
            Long publisherId = 1L;
            String publisherName = "name placeholder";
            String publisherDescription = "description placeholder";
            UpdatePublisherInput updatePublisherInput = new UpdatePublisherInput(publisherName, publisherDescription);
            Optional<Publisher> optionalPublisherFounded = Optional.of(new Publisher(publisherId, publisherName, publisherDescription));
            Publisher publisherToUpdate = new Publisher(publisherId, publisherName, publisherDescription);
            Publisher publisherUpdated = new Publisher(publisherId, publisherName, publisherDescription);

            when(publisherRepository.findById(publisherId)).thenReturn(optionalPublisherFounded);
            when(publisherRepository.save(publisherToUpdate)).thenReturn(publisherUpdated);

            updatePublisher.execute(publisherId, updatePublisherInput);
            verify(publisherRepository, times(1)).findById(publisherId);
            verifyNoMoreInteractions(publisherRepository);
        }
    }

    @Nested
    @DisplayName("Fail Paths")
    class FailPaths {

        @Test
        @DisplayName("It should propagate the exception when the repository fails to connect")
        void it_should_propagate_exception_when_repository_fails() {
            Long publisherId = 1L;
            String publisherName = "name placeholder";
            String publisherDescription = "description placeholder";
            UpdatePublisherInput updatePublisherInput = new UpdatePublisherInput(publisherName, publisherDescription);
            when(publisherRepository.findById(publisherId)).thenThrow(new RuntimeException("Falha ao acessar o banco de dados"));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> updatePublisher.execute(publisherId, updatePublisherInput));
            assertEquals("Falha ao acessar o banco de dados", exception.getMessage());
        }

        @Test
        @DisplayName("It should throw exception when publisher not found")
        void it_should_throw_exception_when_publisher_not_found() {
            Long publisherId = 1L;
            when(publisherRepository.findById(publisherId)).thenThrow(new PublisherNotFoundException("Publisher not found with id: " + publisherId));

            assertThatThrownBy(() -> updatePublisher.execute(publisherId, new UpdatePublisherInput("name", "description")))
                    .isInstanceOf(PublisherNotFoundException.class)
                    .hasMessage("Publisher not found with id: " + publisherId);
        }
    }
}
