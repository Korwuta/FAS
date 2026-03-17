package com.example.FAS.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tax {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(unique = true)
    @NotBlank
    private String taxCode;
    @NotBlank
    private String taxName;
    @NotNull
    private TaxRate taxRate;
    @NotNull
    private TaxPayType taxPayType;
    @JoinColumn(name = "tax_account_id")
    @ManyToOne
    private Account taxAccount;
    private boolean enabled;
}
