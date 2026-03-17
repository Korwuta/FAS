package com.example.FAS.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class EntityStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;
}
