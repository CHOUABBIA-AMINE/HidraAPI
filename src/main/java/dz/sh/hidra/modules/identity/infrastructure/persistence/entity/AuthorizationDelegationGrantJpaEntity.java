/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationDelegationGrantJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AuthorizationDelegationGrant.
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
     * Database-backed JPA entity for AuthorizationDelegationGrant.
     */
    @Entity
    @Table(name = "hidra_identity_authorization_delegation_grant")
    public class AuthorizationDelegationGrantJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "delegator_user_id", nullable = false, length = 80)
    private String delegatorUserId;

    @Column(name = "delegate_user_id", nullable = false, length = 80)
    private String delegateUserId;

    @Column(name = "permission_id", nullable = true, length = 80)
    private String permissionId;

    @Column(name = "role_id", nullable = true, length = 80)
    private String roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope_type", nullable = true, length = 80)
    private ScopeType scopeType;

    @Column(name = "scope_reference_id", nullable = true, length = 120)
    private String scopeReferenceId;

    @Column(name = "scope_code_snapshot", nullable = true, length = 120)
    private String scopeCodeSnapshot;

    @Column(name = "approved_by_workflow_id", nullable = true, length = 120)
    private String approvedByWorkflowId;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private DelegationStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "revoked_at", nullable = true)
    private Instant revokedAt;

        protected AuthorizationDelegationGrantJpaEntity() {
            // Required by JPA.
        }

        public AuthorizationDelegationGrantJpaEntity(
                String id,
            String delegatorUserId,
            String delegateUserId,
            String permissionId,
            String roleId,
            ScopeType scopeType,
            String scopeReferenceId,
            String scopeCodeSnapshot,
            String approvedByWorkflowId,
            Instant validFrom,
            Instant validTo,
            DelegationStatus status,
            Instant createdAt,
            Instant revokedAt
        ) {
            this.id = id;
        this.delegatorUserId = delegatorUserId;
        this.delegateUserId = delegateUserId;
        this.permissionId = permissionId;
        this.roleId = roleId;
        this.scopeType = scopeType;
        this.scopeReferenceId = scopeReferenceId;
        this.scopeCodeSnapshot = scopeCodeSnapshot;
        this.approvedByWorkflowId = approvedByWorkflowId;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.status = status;
        this.createdAt = createdAt;
        this.revokedAt = revokedAt;
        }


    public String id() {
        return id;
    }


    public String delegatorUserId() {
        return delegatorUserId;
    }


    public String delegateUserId() {
        return delegateUserId;
    }


    public String permissionId() {
        return permissionId;
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


    public String approvedByWorkflowId() {
        return approvedByWorkflowId;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public DelegationStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant revokedAt() {
        return revokedAt;
    }

    }
