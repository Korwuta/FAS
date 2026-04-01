package com.example.FAS.repository;

import com.example.FAS.model.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalEntryRepository extends JpaRepository<JournalEntry,Long> {
    boolean existsByReferenceAndIdNot(String reference, Long id);
}
