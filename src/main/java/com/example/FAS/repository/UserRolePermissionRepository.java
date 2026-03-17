package com.example.FAS.repository;

import com.example.FAS.model.Permission;
import com.example.FAS.model.UserRole;
import com.example.FAS.model.UserRolePermission;
import com.example.FAS.model.UserRolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolePermissionRepository extends JpaRepository<UserRolePermission, UserRolePermissionId> {
    boolean existsByUserRoleAndPermission(UserRole userRole, Permission permission);
}
