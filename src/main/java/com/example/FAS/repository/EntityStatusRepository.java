package com.example.FAS.repository;

import com.example.FAS.model.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntityStatusRepository extends JpaRepository<EntityStatus,Long> {
    Optional<EntityStatus> findByName(String name);
}
