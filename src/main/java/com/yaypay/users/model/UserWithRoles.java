package com.yaypay.users.model;

import lombok.Data;

import java.util.Set;

@Data
public class UserWithRoles {
    private User user;            // L'objet utilisateur
    private Set<String> roleNames; // Les noms des rôles associés
}
