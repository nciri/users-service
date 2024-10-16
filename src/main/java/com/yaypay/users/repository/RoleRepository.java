package com.yaypay.users.repository;

import com.yaypay.users.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName); // Trouver un rôle par son nom
}
