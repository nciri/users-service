
package com.yaypay.users.service;

import com.yaypay.users.model.Role;
import com.yaypay.users.model.User;

import com.yaypay.users.repository.RoleRepository;
import com.yaypay.users.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user, Set<String> roleNames) {
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash())); // Encoder le mot de passe
        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = roleRepository.findByRoleName(roleName);
            if (role != null) {
                roles.add(role);
            }
        }
        user.setRoles(roles);
        return userRepository.save(user); // Enregistrer l'utilisateur
    }

    public User updateUser(User user) {
        return userRepository.save(user); // Mettre à jour l'utilisateur dans la base de données
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id); // Récupérer un utilisateur par ID
    }

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByUsername(username)); // Trouver par username
    }

    public List<User> getAllUsers() {
        return userRepository.findAll(); // Récupérer tous les utilisateurs
    }

    public Set<Role> getUserRoles(User user) {
        return user.getRoles();
    }
    
}
