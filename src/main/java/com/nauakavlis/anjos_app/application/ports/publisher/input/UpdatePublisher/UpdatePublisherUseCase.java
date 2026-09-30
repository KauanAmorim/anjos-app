package com.nauakavlis.anjos_app.application.ports.publisher.in.UpdatePublisher;

public interface UpdatePublisherUseCase {
    UpdatePublisherOutput execute(Long id, UpdatePublisherInput input);
}
