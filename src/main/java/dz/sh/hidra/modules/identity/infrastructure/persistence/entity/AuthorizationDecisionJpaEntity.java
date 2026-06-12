/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationDecisionJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AuthorizationDecision.
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
     * Database-backed JPA entity for AuthorizationDecision.
     */
    @Entity
    @Table(name = "hidra_identity_authorization_decision")
    public class AuthorizationDecisionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "user_id", nullable = false, length = 80)
    private String userId;

    @Column(name = "permission_code", nullable = false, length = 160)
    private String permissionCode;

    @Column(name = "resource_type", nullable = true, length = 120)
    private String resourceType;

    @Column(name = "resource_reference_id", nullable = true, length = 120)
    private String resourceReferenceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope_type", nullable = true, length = 80)
    private ScopeType scopeType;

    @Column(name = "scope_reference_id", nullable = true, length = 120)
    private String scopeReferenceId;

    @Column(name = "scope_code_snapshot", nullable = true, length = 120)
    private String scopeCodeSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "decision", nullable = false, length = 40)
    private AuthorizationDecisionValue decision;

    @Column(name = "reason_code", nullable = true, length = 120)
    private String reasonCode;

    @Column(name = "reason_message", nullable = true, columnDefinition = "text")
    private String reasonMessage;

    @Column(name = "matched_grant_ids", nullable = true, columnDefinition = "jsonb")
    private String matchedGrantIds;

    @Column(name = "matched_policy_rule_ids", nullable = true, columnDefinition = "jsonb")
    private String matchedPolicyRuleIds;

    @Column(name = "external_claims_used", nullable = true, columnDefinition = "jsonb")
    private String externalClaimsUsed;

    @Column(name = "evaluated_at", nullable = false)
    private Instant evaluatedAt;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

    @Column(name = "request_id", nullable = true, length = 120)
    private String requestId;

        protected AuthorizationDecisionJpaEntity() {
            // Required by JPA.
        }

        public AuthorizationDecisionJpaEntity(
                String id,
            String userId,
            String permissionCode,
            String resourceType,
            String resourceReferenceId,
            ScopeType scopeType,
            String scopeReferenceId,
            String scopeCodeSnapshot,
            AuthorizationDecisionValue decision,
            String reasonCode,
            String reasonMessage,
            String matchedGrantIds,
            String matchedPolicyRuleIds,
            String externalClaimsUsed,
            Instant evaluatedAt,
            String correlationId,
            String requestId
        ) {
            this.id = id;
        this.userId = userId;
        this.permissionCode = permissionCode;
        this.resourceType = resourceType;
        this.resourceReferenceId = resourceReferenceId;
        this.scopeType = scopeType;
        this.scopeReferenceId = scopeReferenceId;
        this.scopeCodeSnapshot = scopeCodeSnapshot;
        this.decision = decision;
        this.reasonCode = reasonCode;
        this.reasonMessage = reasonMessage;
        this.matchedGrantIds = matchedGrantIds;
        this.matchedPolicyRuleIds = matchedPolicyRuleIds;
        this.externalClaimsUsed = externalClaimsUsed;
        this.evaluatedAt = evaluatedAt;
        this.correlationId = correlationId;
        this.requestId = requestId;
        }


    public String id() {
        return id;
    }


    public String userId() {
        return userId;
    }


    public String permissionCode() {
        return permissionCode;
    }


    public String resourceType() {
        return resourceType;
    }


    public String resourceReferenceId() {
        return resourceReferenceId;
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


    public AuthorizationDecisionValue decision() {
        return decision;
    }


    public String reasonCode() {
        return reasonCode;
    }


    public String reasonMessage() {
        return reasonMessage;
    }


    public String matchedGrantIds() {
        return matchedGrantIds;
    }


    public String matchedPolicyRuleIds() {
        return matchedPolicyRuleIds;
    }


    public String externalClaimsUsed() {
        return externalClaimsUsed;
    }


    public Instant evaluatedAt() {
        return evaluatedAt;
    }


    public String correlationId() {
        return correlationId;
    }


    public String requestId() {
        return requestId;
    }

    }
