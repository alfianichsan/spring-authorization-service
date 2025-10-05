package com.authorization.authorization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authorization.authorization.entity.mapping.RoleUser;

public interface RoleUserRepository extends JpaRepository<RoleUser, Long> {
    List<RoleUser> findByUserId(Long userId);
}
