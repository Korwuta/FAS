package com.example.FAS.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRolePermissionId implements Serializable {
    @Column(name = "user_role_id")
    private Long userRoleId;
    @Column(name = "permission_id")
    private Long permissionId;
}
