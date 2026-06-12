/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationPolicyVersionJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AuthorizationPolicyVersion.
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
     * Database-backed JPA entity for AuthorizationPolicyVersion.
     */
    @Entity
    @Table(name = "hidra_identity_authorization_policy_version")
    public class AuthorizationPolicyVersionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "policy_id", nullable = false, length = 80)
    private String policyId;

    @Column(name = "version_number", nullable = false)
    private int versionNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PolicyVersionStatus status;

    @Column(name = "effective_from", nullable = false)
    private Instant effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private Instant effectiveTo;

    @Column(name = "approved_by_workflow_id", nullable = true, length = 120)
    private String approvedByWorkflowId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "activated_at", nullable = true)
    private Instant activatedAt;

        protected AuthorizationPolicyVersionJpaEntity() {
            // Required by JPA.
        }

        public AuthorizationPolicyVersionJpaEntity(
                String id,
            String policyId,
            int versionNumber,
            PolicyVersionStatus status,
            Instant effectiveFrom,
            Instant effectiveTo,
            String approvedByWorkflowId,
            Instant createdAt,
            Instant activatedAt
        ) {
            this.id = id;
        this.policyId = policyId;
        this.versionNumber = versionNumber;
        this.status = status;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.approvedByWorkflowId = approvedByWorkflowId;
        this.createdAt = createdAt;
        this.activatedAt = activatedAt;
        }


    public String id() {
        return id;
    }


    public String policyId() {
        return policyId;
    }


    public int versionNumber() {
        return versionNumber;
    }


    public PolicyVersionStatus status() {
        return status;
    }


    public Instant effectiveFrom() {
        return effectiveFrom;
    }


    public Instant effectiveTo() {
        return effectiveTo;
    }


    public String approvedByWorkflowId() {
        return approvedByWorkflowId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant activatedAt() {
        return activatedAt;
    }

    }
