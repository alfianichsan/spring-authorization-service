package com.authorization.authorization.entity.mapping;

import java.sql.Date;

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
@Table(name = "m_role_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "t_user_id", nullable = false)
    private Long userId;

    @Column(name = "t_role_id", nullable = false)
    private Long roleId;

    @Column(name = "assigned_at")
    private Date assignedAt;

    @Column(name = "revoke_at")
    private Date revoke_at;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @Column(name = "created_by")
    private Date createdBy;

    @Column(name = "updated_by")
    private Date updatedBy;
}
