package com.nauakavlis.anjos_app.application.ports.publisher.in.GetAllPublishers;

import java.util.List;

public interface GetAllPublishersUseCase {
  List<GetAllPublishersOutput> execute();
}