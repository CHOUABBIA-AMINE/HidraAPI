/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserPermissionGrantJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for UserPermissionGrant.
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
     * Database-backed JPA entity for UserPermissionGrant.
     */
    @Entity
    @Table(name = "hidra_identity_user_permission_grant")
    public class UserPermissionGrantJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "user_id", nullable = false, length = 80)
    private String userId;

    @Column(name = "permission_id", nullable = false, length = 80)
    private String permissionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "effect", nullable = false, length = 40)
    private GrantEffect effect;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope_type", nullable = true, length = 80)
    private ScopeType scopeType;

    @Column(name = "scope_reference_id", nullable = true, length = 120)
    private String scopeReferenceId;

    @Column(name = "scope_code_snapshot", nullable = true, length = 120)
    private String scopeCodeSnapshot;

    @Column(name = "grant_reason", nullable = false, columnDefinition = "text")
    private String grantReason;

    @Column(name = "approved_by_workflow_id", nullable = true, length = 120)
    private String approvedByWorkflowId;

    @Column(name = "emergency_access", nullable = false)
    private boolean emergencyAccess;

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

        protected UserPermissionGrantJpaEntity() {
            // Required by JPA.
        }

        public UserPermissionGrantJpaEntity(
                String id,
            String userId,
            String permissionId,
            GrantEffect effect,
            ScopeType scopeType,
            String scopeReferenceId,
            String scopeCodeSnapshot,
            String grantReason,
            String approvedByWorkflowId,
            boolean emergencyAccess,
            Instant validFrom,
            Instant validTo,
            GrantStatus status,
            Instant createdAt,
            Instant revokedAt
        ) {
            this.id = id;
        this.userId = userId;
        this.permissionId = permissionId;
        this.effect = effect;
        this.scopeType = scopeType;
        this.scopeReferenceId = scopeReferenceId;
        this.scopeCodeSnapshot = scopeCodeSnapshot;
        this.grantReason = grantReason;
        this.approvedByWorkflowId = approvedByWorkflowId;
        this.emergencyAccess = emergencyAccess;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.status = status;
        this.createdAt = createdAt;
        this.revokedAt = revokedAt;
        }


    public String id() {
        return id;
    }


    public String userId() {
        return userId;
    }


    public String permissionId() {
        return permissionId;
    }


    public GrantEffect effect() {
        return effect;
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


    public boolean emergencyAccess() {
        return emergencyAccess;
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

    }
