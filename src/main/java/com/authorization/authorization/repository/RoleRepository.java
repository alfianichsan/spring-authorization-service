package com.authorization.authorization.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authorization.authorization.entity.Roles;

public interface RoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByRoleName(String roleName);

}
