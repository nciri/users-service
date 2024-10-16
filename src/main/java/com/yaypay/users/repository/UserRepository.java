package com.yaypay.users.repository;

import com.yaypay.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email); // Trouver un utilisateur par email
    User findByUsername(String username); // Trouver un utilisateur par username
}
