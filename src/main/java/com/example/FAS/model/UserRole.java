package com.example.FAS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank
    private String name;
    private boolean enabled;
    @JoinColumn(name = "branch_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Branch branch;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role_navigation",joinColumns = @JoinColumn(name = "user_role_id"),
            inverseJoinColumns = @JoinColumn(name="navigation_id"),uniqueConstraints = {})
    private List<Navigation> navigations = new ArrayList<>();
}
