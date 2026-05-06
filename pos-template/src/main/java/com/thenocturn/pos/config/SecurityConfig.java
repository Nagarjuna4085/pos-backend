package com.thenocturn.pos.config;

import com.thenocturn.pos.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authorizeHttpRequests(auth -> auth

                // PUBLIC
                .requestMatchers("/api/auth/**").permitAll()
                

                // ✅ IMPORTANT: allow static files
                .requestMatchers("/uploads/**").permitAll()

                // ADMIN ONLY
                .requestMatchers("/api/dashboard/**").hasRole("ADMIN")
                .requestMatchers("/api/inventory/**").hasRole("ADMIN")

                // ADMIN + CASHIER
                .requestMatchers("/api/products/**").hasAnyRole("ADMIN", "CASHIER")
                .requestMatchers("/api/orders/**").hasAnyRole("ADMIN", "CASHIER")

                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter,
                    UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}