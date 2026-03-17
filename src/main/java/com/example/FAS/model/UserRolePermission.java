package com.example.FAS.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRolePermission {
    @EmbeddedId
    private UserRolePermissionId id;
    @OneToOne
    @JoinColumn(name = "user_role_id")
    @MapsId("userRoleId")
    private UserRole userRole;
    @OneToOne
    @JoinColumn(name = "permission_id")
    @MapsId("permissionId")
    private Permission permission;
    private LocalDateTime createdAt;
    public UserRolePermission(UserRole userRole, Permission permission){
        this.id = new UserRolePermissionId(userRole.getId(),permission.getId());
        this.userRole = userRole;
        this.permission = permission;
        this.createdAt = LocalDateTime.now();
    }
}
