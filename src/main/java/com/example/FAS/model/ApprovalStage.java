package com.example.FAS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalStage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;
    @JoinColumn(name = "approval_status_id")
    @ManyToOne
    private EntityStatus entityStatus;
    @Enumerated(EnumType.STRING)
    private Entities entity;
}
