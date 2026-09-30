package com.nauakavlis.anjos_app.application.services.publisher;

import com.nauakavlis.anjos_app.application.ports.publisher.in.CreatePublisher.CreatePublisherInput;
import com.nauakavlis.anjos_app.application.ports.publisher.in.DeletePublisher.DeletePublisherOutput;
import com.nauakavlis.anjos_app.application.ports.publisher.in.UpdatePublisher.UpdatePublisherInput;
import com.nauakavlis.anjos_app.application.ports.publisher.in.UpdatePublisher.UpdatePublisherOutput;
import com.nauakavlis.anjos_app.application.ports.publisher.out.PublisherRepository;
import com.nauakavlis.anjos_app.domain.model.Publisher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class DeletePublisherTest {

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private DeletePublisher deletePublisher;

    @Nested
    @DisplayName("Success Paths")
    class SuccessPaths {

        @Test
        @DisplayName("It should update publisher")
        void it_should_update_publisher() {
            Long publisherId = 1L;

            when(publisherRepository.deleteById(publisherId)).thenReturn(true);
            boolean result = deletePublisher.execute(publisherId);
            assertTrue(result);
        }

        @Test
        @DisplayName("It should call repository one time")
        void it_should_call_repository_one_time() {
            Long publisherId = 1L;

            when(publisherRepository.deleteById(publisherId)).thenReturn(true);
            deletePublisher.execute(publisherId);

            verify(publisherRepository, times(1)).deleteById(publisherId);
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
            when(publisherRepository.deleteById(publisherId)).thenThrow(new RuntimeException("Falha ao acessar o banco de dados"));
            RuntimeException exception = assertThrows(RuntimeException.class, () -> deletePublisher.execute(publisherId));
            assertEquals("Falha ao acessar o banco de dados", exception.getMessage());
        }
    }
}
