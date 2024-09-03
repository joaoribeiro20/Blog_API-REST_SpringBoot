package com.api.blog.portfolio.blogApi.repositories.permission;

import com.api.blog.portfolio.blogApi.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, String> {
}
