package com.api.blog.portfolio.blogApi.services.role;
import com.api.blog.portfolio.blogApi.entities.Role;
import com.api.blog.portfolio.blogApi.entities.user.User;
import com.api.blog.portfolio.blogApi.infra.exception.genericExceptions.ResourceAlreadyExistsException;
import com.api.blog.portfolio.blogApi.infra.security.SecurityFilter;
import com.api.blog.portfolio.blogApi.repositories.role.RoleRepository;
import com.api.blog.portfolio.blogApi.repositories.user.UserRepositorie;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.blog.portfolio.blogApi.infra.exception.genericExceptions.ResourceNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    SecurityFilter securityFilter;
    @Autowired
    UserRepositorie userRepositorie;

    public User assignRoleToUser(String nameRole, String idUser) throws ResourceNotFoundException {

        User user = userRepositorie.getReferenceByEmail(idUser);
        System.out.println(user);

        Role userRole = roleRepository.findById(nameRole)
                .orElseThrow(ResourceNotFoundException::new);

        System.out.println(userRole);
        Set<Role> roles = user.getRoles();  // Presumindo que o método getRoles() retorna o conjunto de roles
        System.out.println(roles);
        if (roles == null) {
           throw new ResourceNotFoundException();
        }
        roles.add(userRole);
        user.setRoles(roles);

        // Salvar o usuário atualizado no repositório
        userRepositorie.save(user);

        return user;
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(String id) {
        return roleRepository.findById(id);
    }

    public void deleteRole(String id) {
        roleRepository.deleteById(id);
    }
}
