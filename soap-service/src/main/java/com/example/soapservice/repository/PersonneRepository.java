package com.example.soapservice.repository;

import com.example.soapservice.model.PersonneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository Spring Data JPA pour l'entité PersonneEntity.
 */
@Repository
public interface PersonneRepository extends JpaRepository<PersonneEntity, Long> {
}
