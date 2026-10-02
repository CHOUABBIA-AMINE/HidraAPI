/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthorizationPolicyRuleJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AuthorizationPolicyRule.
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
     * Database-backed JPA entity for AuthorizationPolicyRule.
     */
    @Entity
    @Table(name = "hidra_identity_authorization_policy_rule")
    public class AuthorizationPolicyRuleJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "policy_version_id", nullable = false, length = 80)
    private String policyVersionId;

    @Column(name = "rule_code", nullable = false, length = 120)
    private String ruleCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "effect", nullable = false, length = 40)
    private PolicyRuleEffect effect;

    @Column(name = "priority", nullable = false)
    private int priority;

    @Column(name = "subject_expression", nullable = true, columnDefinition = "jsonb")
    private String subjectExpression;

    @Column(name = "resource_expression", nullable = true, columnDefinition = "jsonb")
    private String resourceExpression;

    @Column(name = "action_expression", nullable = true, columnDefinition = "jsonb")
    private String actionExpression;

    @Column(name = "context_expression", nullable = true, columnDefinition = "jsonb")
    private String contextExpression;

    @Column(name = "obligation_expression", nullable = true, columnDefinition = "jsonb")
    private String obligationExpression;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private PolicyRuleStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected AuthorizationPolicyRuleJpaEntity() {
            // Required by JPA.
        }

        public AuthorizationPolicyRuleJpaEntity(
                String id,
            String policyVersionId,
            String ruleCode,
            PolicyRuleEffect effect,
            int priority,
            String subjectExpression,
            String resourceExpression,
            String actionExpression,
            String contextExpression,
            String obligationExpression,
            PolicyRuleStatus status,
            Instant createdAt
        ) {
            this.id = id;
        this.policyVersionId = policyVersionId;
        this.ruleCode = ruleCode;
        this.effect = effect;
        this.priority = priority;
        this.subjectExpression = subjectExpression;
        this.resourceExpression = resourceExpression;
        this.actionExpression = actionExpression;
        this.contextExpression = contextExpression;
        this.obligationExpression = obligationExpression;
        this.status = status;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String policyVersionId() {
        return policyVersionId;
    }


    public String ruleCode() {
        return ruleCode;
    }


    public PolicyRuleEffect effect() {
        return effect;
    }


    public int priority() {
        return priority;
    }


    public String subjectExpression() {
        return subjectExpression;
    }


    public String resourceExpression() {
        return resourceExpression;
    }


    public String actionExpression() {
        return actionExpression;
    }


    public String contextExpression() {
        return contextExpression;
    }


    public String obligationExpression() {
        return obligationExpression;
    }


    public PolicyRuleStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
