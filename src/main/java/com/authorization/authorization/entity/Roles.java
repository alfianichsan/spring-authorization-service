package com.authorization.authorization.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

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
