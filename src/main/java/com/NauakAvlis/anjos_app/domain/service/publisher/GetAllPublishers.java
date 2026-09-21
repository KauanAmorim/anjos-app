package com.NauakAvlis.anjos_app.domain.service.publisher;

import com.NauakAvlis.anjos_app.application.ports.publisher.in.GetAllPublishers.GetAllPublishersOutPut;
import com.NauakAvlis.anjos_app.application.ports.publisher.in.GetAllPublishers.GetAllPublishersUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllPublishers implements GetAllPublishersUseCase {


    @Override
    public List<GetAllPublishersOutPut> execute() {
        return List.of();
    }
}
