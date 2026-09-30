package com.nauakavlis.anjos_app.application.services.publisher;

import com.nauakavlis.anjos_app.application.ports.publisher.in.GetPublisherById.GetPublisherByIdOutput;
import com.nauakavlis.anjos_app.application.ports.publisher.out.PublisherRepository;
import com.nauakavlis.anjos_app.domain.exception.publisher.PublisherNotFoundException;
import com.nauakavlis.anjos_app.domain.model.Publisher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class GetPublisherByIdTest {

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private GetPublisherById getPublisherById;

    @Nested
    @DisplayName("Success Paths")
    class SuccessPaths {

        @Test
        @DisplayName("It should get a publisher by id")
        void it_should_get_a_publisher_by_id() {
            Long publisherId = 1L;
            String publisherName = "name placeholder";
            String publisherDescription = "description placeholder";

            Optional<Publisher> publisherFounded = Optional.of(new Publisher(1L, publisherName, publisherDescription));
            when(publisherRepository.findById(publisherId)).thenReturn(publisherFounded);

            GetPublisherByIdOutput result = getPublisherById.execute(publisherId);

            assertEquals(publisherFounded.get().id(), result.id());
            assertEquals(publisherFounded.get().name(), result.name());
            assertEquals(publisherFounded.get().description(), result.description());
        }

        @Test
        @DisplayName("It should call repository one time")
        void it_should_call_repository_one_time() {
            Long publisherId = 1L;
            String publisherName = "name placeholder";
            String publisherDescription = "description placeholder";

            Optional<Publisher> publisherFounded = Optional.of(new Publisher(1L, publisherName, publisherDescription));
            when(publisherRepository.findById(publisherId)).thenReturn(publisherFounded);

            getPublisherById.execute(publisherId);
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
            when(publisherRepository.findById(publisherId)).thenThrow(new RuntimeException("Falha ao acessar o banco de dados"));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> getPublisherById.execute(publisherId));
            assertEquals("Falha ao acessar o banco de dados", exception.getMessage());
        }

        @Test
        @DisplayName("It should throw exception when publisher not found")
        void it_should_throw_exception_when_publisher_not_found() {
            Long publisherId = 1L;
            when(publisherRepository.findById(publisherId)).thenThrow(new PublisherNotFoundException("Publisher not found with id: " + publisherId));

            assertThatThrownBy(() -> getPublisherById.execute(publisherId))
                    .isInstanceOf(PublisherNotFoundException.class)
                    .hasMessage("Publisher not found with id: " + publisherId);
        }
    }
}
