package com.authorization.authorization.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authorization.authorization.entity.mapping.RoleApi;

public interface RoleApiRepository extends JpaRepository<RoleApi, Long> {
    List<RoleApi> findByRoleId(Long roleId);
}
