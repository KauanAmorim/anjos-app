package com.nauakavlis.anjos_app.infrastructure.adapters.author.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorJpaRepository extends JpaRepository<AuthorEntity, Long> { }
