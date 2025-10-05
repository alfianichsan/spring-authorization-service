package com.authorization.authorization.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.authorization.authorization.entity.Apis;
import com.authorization.authorization.entity.mapping.RoleUser;
import com.authorization.authorization.repository.ApiRepository;
import com.authorization.authorization.repository.RoleApiRepository;
import com.authorization.authorization.repository.RoleUserRepository;

@Service
public class AuthorizationService {
    private final RoleUserRepository roleUserRepository;
    private final RoleApiRepository roleApiRepository;
    private final ApiRepository apiRepository;

    public AuthorizationService(RoleUserRepository roleUserRepository,
            RoleApiRepository roleApiRepository,
            ApiRepository apiRepository) {
        this.roleUserRepository = roleUserRepository;
        this.roleApiRepository = roleApiRepository;
        this.apiRepository = apiRepository;
    }

    public boolean isAllowedAccess(Long userId, String path, String method) {
        // Cari definisi API
        String basePath = path.contains("/") ? path.substring(0, path.lastIndexOf("/")) : path;
        Optional<Apis> apiOpt = apiRepository.findApiByPathAndMethod(path, basePath, method);
        if (apiOpt.isEmpty())
            return false;

        if (!apiOpt.get().isAuth()) {
            return true;
        }
        Long apiId = apiOpt.get().getId();

        // Ambil semua role user
        List<RoleUser> userRoles = roleUserRepository.findByUserId(userId);

        // Cek apakah salah satu role punya izin ke API ini
        return userRoles.stream()
                .anyMatch(ru -> roleApiRepository.findByRoleId(ru.getRoleId())
                        .stream()
                        .anyMatch(r -> r.getApiId().equals(apiId)));
    }
}
