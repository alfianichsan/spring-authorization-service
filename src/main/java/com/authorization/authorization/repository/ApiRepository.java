package com.authorization.authorization.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.authorization.authorization.entity.Apis;

public interface ApiRepository extends JpaRepository<Apis, Long> {
    Optional<Apis> findByPathAndMethod(String path, String method);

    @Query("SELECT a FROM Apis a " +
            "WHERE a.method = :method " +
            "AND (a.path = :path OR (a.path LIKE CONCAT(:basePath, '/*') AND :path LIKE CONCAT(:basePath, '/%')))")
    Optional<Apis> findApiByPathAndMethod(@Param("path") String path,
            @Param("basePath") String basePath,
            @Param("method") String method);
}
