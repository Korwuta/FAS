package com.example.FAS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkflowRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @JoinColumn(name = "user_role_id")
    @ManyToOne
    private UserRole userRole;
    @JoinColumn(name = "from_stage_id")
    @ManyToOne
    private ApprovalStage fromStage;
    @JoinColumn(name = "to_stage_id")
    @ManyToOne
    private ApprovalStage toStage;
    private String description;
    @Enumerated(EnumType.STRING)
    private Entities entity;
}
