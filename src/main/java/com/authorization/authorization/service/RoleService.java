package com.authorization.authorization.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.authorization.authorization.entity.Roles;
import com.authorization.authorization.repository.RoleRepository;
import com.authorization.authorization.utils.ResourceNotFoundException;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Roles creatRoles(Roles roles) {
        if (roleRepository.findByRoleName(roles.getRoleName()).isPresent()) {
            throw new RuntimeException("Role name already exists");
        }

        Roles newRole = Roles.builder()
                .roleName(roles.getRoleName().toUpperCase())
                .description(roles.getDescription())
                .createdAt(new Date()).build();
        return roleRepository.save(newRole);
    }

    public List<Roles> listRoles() {
        return roleRepository.findAll();
    }

    public Roles getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role with ID " + id + " not found"));
    }
}
