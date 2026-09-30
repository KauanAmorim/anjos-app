package com.nauakavlis.anjos_app.application.services.publisher;

import com.nauakavlis.anjos_app.application.ports.publisher.input.GetAllPublishers.GetAllPublishersOutput;
import com.nauakavlis.anjos_app.application.ports.publisher.output.PublisherRepository;
import com.nauakavlis.anjos_app.domain.model.Publisher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class GetAllPublishersTest {

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private GetAllPublishers getAllPublishers;

    @Nested
    @DisplayName("Success Paths")
    class SuccessPaths {

        @Test
        @DisplayName("Should return all publishers")
        void shouldReturnAllPublishers() {

            Long idPub1 = 1L;
            Long idPub2 = 2L;

            String namePub1 = "Pub 1";
            String namePub2 = "Pub 2";

            String descPub1 = "";
            String descPub2 = "Desc 2";

            Publisher pub1 = new Publisher(idPub1, namePub1, descPub1);
            Publisher pub2 = new Publisher(idPub2, namePub2, descPub2);

            when(publisherRepository.findAll()).thenReturn(List.of(pub1, pub2));
            List<GetAllPublishersOutput> result = getAllPublishers.execute();

            assertThat(result)
                .hasSize(2)
                .extracting(
                    GetAllPublishersOutput::id,
                    GetAllPublishersOutput::name,
                    GetAllPublishersOutput::description
                )
                .containsExactly(
                    tuple(idPub1, namePub1, descPub1),
                    tuple(idPub2, namePub2, descPub2)
                );
        }

        @Test
        @DisplayName("It should call repository one time")
        void it_should_call_repository_one_time() {
            Long idPub1 = 1L;
            Long idPub2 = 2L;

            String namePub1 = "Pub 1";
            String namePub2 = "Pub 2";

            String descPub1 = "";
            String descPub2 = "Desc 2";

            Publisher pub1 = new Publisher(idPub1, namePub1, descPub1);
            Publisher pub2 = new Publisher(idPub2, namePub2, descPub2);

            when(publisherRepository.findAll()).thenReturn(List.of(pub1, pub2));
            getAllPublishers.execute();
            verify(publisherRepository, times(1)).findAll();
            verifyNoMoreInteractions(publisherRepository);
        }
    }

    @Nested
    @DisplayName("Fail Paths")
    class FailPaths {

        @Test
        @DisplayName("It should propagate the exception when the repository fails to connect")
        void it_should_propagate_exception_when_repository_fails() {
            when(publisherRepository.findAll()).thenThrow(new RuntimeException("Falha ao acessar o banco de dados"));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> getAllPublishers.execute());
            assertEquals("Falha ao acessar o banco de dados", exception.getMessage());
        }
    }
}
