package com.nauakavlis.anjos_app.infrastructure.adapters.publisher.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherJpaRepository extends JpaRepository<PublisherEntity, Long> { }
