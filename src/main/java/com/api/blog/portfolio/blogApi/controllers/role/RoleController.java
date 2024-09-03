package com.api.blog.portfolio.blogApi.controllers.role;

import com.api.blog.portfolio.blogApi.entities.Role;
import com.api.blog.portfolio.blogApi.entities.user.User;
import com.api.blog.portfolio.blogApi.services.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/assignToUser")
    public ResponseEntity assignRoleToUser(@RequestBody AssignRoleRequest request) {
        User user = roleService.assignRoleToUser(request.role(), request.user());
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        System.out.println("tesdte");
        Role newRole = roleService.createRole(role);
        System.out.println(newRole);
        return ResponseEntity.ok(newRole);
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable String id) {
        return roleService.getRoleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable String id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
