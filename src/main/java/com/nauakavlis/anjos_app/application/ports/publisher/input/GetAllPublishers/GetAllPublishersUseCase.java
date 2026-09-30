package com.nauakavlis.anjos_app.application.ports.publisher.input.GetAllPublishers;

import java.util.List;

public interface GetAllPublishersUseCase {
  List<GetAllPublishersOutput> execute();
}