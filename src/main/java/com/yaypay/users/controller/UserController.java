package com.yaypay.users.controller;

import com.yaypay.users.model.User;
import com.yaypay.users.model.UserWithRoles;
import com.yaypay.users.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<User> registerUser(@RequestBody UserWithRoles userWithRoles) {
        User createdUser = userService.createUser(userWithRoles.getUser(), userWithRoles.getRoleNames());
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/me")
    public ResponseEntity<User> getAuthenticatedUser(Authentication authentication) {
        Optional<User> user = userService.findByUsername(authentication.getName());
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails, Authentication authentication) {
        Optional<User> existingUser = userService.getUserById(id);

        if (existingUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Utilisateur non trouvé
        }

        User userToUpdate = existingUser.get();

        // Vérifier si l'utilisateur est ADMIN ou si c'est lui-même qui est en train de modifier ses informations
        if (authentication.getAuthorities().stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")) ||
            authentication.getName().equals(userToUpdate.getUsername())) {
            
            userToUpdate.setFullName(userDetails.getFullName()); // Mettre à jour le nom complet
            userToUpdate.setEmail(userDetails.getEmail()); // Mettre à jour l'email (si nécessaire)

            User updatedUser = userService.updateUser(userToUpdate);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN); // L'utilisateur ne peut pas modifier quelqu'un d'autre
    }

}
