package com.authorization.authorization.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.authorization.authorization.utils.JwtAuthFilter;
import com.authorization.authorization.utils.RbacAuthorizationFilter;
import com.authorization.authorization.utils.SecurityWhitelist;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final RbacAuthorizationFilter rbacAuthorizationFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, RbacAuthorizationFilter rbacAuthorizationFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.rbacAuthorizationFilter = rbacAuthorizationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                SecurityWhitelist.ENDPOINTS)
                        .permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(rbacAuthorizationFilter, JwtAuthFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
