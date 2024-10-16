package com.yaypay.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public SecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Désactiver CSRF pour les API REST
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/users").hasRole("ADMIN")  // Seul l'ADMIN peut accéder à /api/users
                .requestMatchers("/api/me").hasRole("USER")  // Seul l'utilisateur USER peut accéder à /api/me
                .anyRequest().authenticated()  // Toutes les autres requêtes nécessitent une authentification
            )
            .formLogin(formLogin -> formLogin
                .successHandler(customAuthenticationSuccessHandler)  // Utilisation du handler personnalisé
            )
            .httpBasic(httpBasicCustomizer -> {})
            .logout(logout -> logout
                .logoutUrl("/api/logout")  // URL pour la déconnexion
                .logoutSuccessUrl("/login?logout")  // URL après une déconnexion réussie
                .invalidateHttpSession(true)  // Invalider la session HTTP
                .deleteCookies("JSESSIONID")  // Supprimer le cookie de session
                .permitAll()  // Autoriser tout le monde à accéder à la déconnexion
            );  // Utilisation de HTTP Basic;  // Utilisation de HTTP Basic pour les API

        return http.build();
    }
}


