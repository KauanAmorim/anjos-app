package com.nauakavlis.anjos_app.application.ports.publisher.input.UpdatePublisher;

import com.nauakavlis.anjos_app.application.ports.publisher.input.CreatePublisher.CreatePublisherInput;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdatePublisherInputTest {
    private Validator validator;


    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }


    @Nested
    @DisplayName("Success Paths")
    class SuccessPaths {

        @Test
        @DisplayName("It should create a UpdatePublisherInput without errors")
        void it_should_create_a_updatepublisherinput_without_errors() {
            String publisherName = "name placeholder";
            String publisherDescription = "description placeholder";

            UpdatePublisherInput input = new UpdatePublisherInput(publisherName, publisherDescription);

            Set<ConstraintViolation<UpdatePublisherInput>> violations = validator.validate(input);
            assertThat(violations)
                    .hasSize(0);
        }
    }


    @Nested
    @DisplayName("Fail Paths")
    class FailPaths {

        @Nested
        @DisplayName("Name Scenarios")
        class NameScenarios {

            @Test
            @DisplayName("It should fail when name is blank")
            void it_should_fail_when_name_is_blank() {
                String publisherName = "";
                String publisherDescription = "description placeholder";

                UpdatePublisherInput input = new UpdatePublisherInput(publisherName, publisherDescription);

                Set<ConstraintViolation<UpdatePublisherInput>> violations = validator.validate(input);
                assertThat(violations)
                        .hasSize(1)
                        .extracting(ConstraintViolation::getMessage)
                        .containsExactly("Name is required");
            }

            @Test
            @DisplayName("It should fail when name exceeds 100 characters")
            void it_should_fail_when_name_exceeds_100_characters() {
                String publisherName = "a".repeat(101);
                String publisherDescription = "description placeholder";

                UpdatePublisherInput input = new UpdatePublisherInput(publisherName, publisherDescription);

                Set<ConstraintViolation<UpdatePublisherInput>> violations = validator.validate(input);
                assertThat(violations)
                        .hasSize(1)
                        .extracting(ConstraintViolation::getMessage)
                        .containsExactly("Name must be at most 100 characters");
            }
        }

        @Nested
        @DisplayName("Description Scenarios")
        class DescriptionScenarios {

            @Test
            @DisplayName("It should fail when description exceeds 255 characters")
            void it_should_fail_when_description_exceeds_255_characters() {
                String publisherName = "name placeholder";
                String publisherDescription = "a".repeat(256);

                UpdatePublisherInput input = new UpdatePublisherInput(publisherName, publisherDescription);

                Set<ConstraintViolation<UpdatePublisherInput>> violations = validator.validate(input);
                assertThat(violations)
                        .hasSize(1)
                        .extracting(ConstraintViolation::getMessage)
                        .containsExactly("Description must be at most 255 characters");
            }
        }
    }
}
