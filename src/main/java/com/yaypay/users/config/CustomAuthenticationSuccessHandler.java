package com.yaypay.users.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Obtenir les rôles de l'utilisateur authentifié
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Parcourir les rôles de l'utilisateur et rediriger en conséquence
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                response.sendRedirect("/api/users");  // Rediriger l'ADMIN vers /api/users
                return;
            } else if (authority.getAuthority().equals("ROLE_USER")) {
                response.sendRedirect("/api/me");  // Rediriger l'utilisateur USER vers /api/me
                return;
            }
        }

        // Redirection par défaut si aucun rôle ne correspond
        response.sendRedirect("/api/logout");
    }
}
