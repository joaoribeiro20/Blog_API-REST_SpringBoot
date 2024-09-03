package com.api.blog.portfolio.blogApi.services.permission;

import com.api.blog.portfolio.blogApi.entities.Permission;
import com.api.blog.portfolio.blogApi.repositories.permission.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public Permission createPermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    public Optional<Permission> getPermissionById(String id) {
        return permissionRepository.findById(id);
    }

    public void deletePermission(String id) {
        permissionRepository.deleteById(id);
    }
}