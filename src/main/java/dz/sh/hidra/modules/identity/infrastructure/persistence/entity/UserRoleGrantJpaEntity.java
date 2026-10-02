/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserRoleGrantJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for UserRoleGrant.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.entity;

import dz.sh.hidra.modules.identity.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for UserRoleGrant.
     */
    @Entity
    @Table(name = "hidra_identity_user_role_grant")
    public class UserRoleGrantJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "user_id", nullable = false, length = 80)
    private String userId;

    @Column(name = "role_id", nullable = false, length = 80)
    private String roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope_type", nullable = true, length = 80)
    private ScopeType scopeType;

    @Column(name = "scope_reference_id", nullable = true, length = 120)
    private String scopeReferenceId;

    @Column(name = "scope_code_snapshot", nullable = true, length = 120)
    private String scopeCodeSnapshot;

    @Column(name = "grant_reason", nullable = true, columnDefinition = "text")
    private String grantReason;

    @Column(name = "approved_by_workflow_id", nullable = true, length = 120)
    private String approvedByWorkflowId;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private GrantStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "revoked_at", nullable = true)
    private Instant revokedAt;

    @Column(name = "revoked_reason", nullable = true, columnDefinition = "text")
    private String revokedReason;

        protected UserRoleGrantJpaEntity() {
            // Required by JPA.
        }

        public UserRoleGrantJpaEntity(
                String id,
            String userId,
            String roleId,
            ScopeType scopeType,
            String scopeReferenceId,
            String scopeCodeSnapshot,
            String grantReason,
            String approvedByWorkflowId,
            Instant validFrom,
            Instant validTo,
            GrantStatus status,
            Instant createdAt,
            Instant revokedAt,
            String revokedReason
        ) {
            this.id = id;
        this.userId = userId;
        this.roleId = roleId;
        this.scopeType = scopeType;
        this.scopeReferenceId = scopeReferenceId;
        this.scopeCodeSnapshot = scopeCodeSnapshot;
        this.grantReason = grantReason;
        this.approvedByWorkflowId = approvedByWorkflowId;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.status = status;
        this.createdAt = createdAt;
        this.revokedAt = revokedAt;
        this.revokedReason = revokedReason;
        }


    public String id() {
        return id;
    }


    public String userId() {
        return userId;
    }


    public String roleId() {
        return roleId;
    }


    public ScopeType scopeType() {
        return scopeType;
    }


    public String scopeReferenceId() {
        return scopeReferenceId;
    }


    public String scopeCodeSnapshot() {
        return scopeCodeSnapshot;
    }


    public String grantReason() {
        return grantReason;
    }


    public String approvedByWorkflowId() {
        return approvedByWorkflowId;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public GrantStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant revokedAt() {
        return revokedAt;
    }


    public String revokedReason() {
        return revokedReason;
    }

    }
