package com.authorization.authorization.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_api")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Apis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path", nullable = false, unique = true)
    private String path;

    @Column(name = "method")
    private String method;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_auth")
    private boolean isAuth;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "created_at", nullable = true)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

    @Column(name = "deleted_at", nullable = true)
    private Date deletedAt;

    @Column(name = "created_by", nullable = true)
    private Date createdBy;

    @Column(name = "updated_by", nullable = true)
    private Date updatedBy;
}
