/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FeatureFlagRuleJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for FeatureFlagRule.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.entity;

import dz.sh.hidra.modules.configuration.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for FeatureFlagRule.
     */
    @Entity
    @Table(name = "hidra_configuration_feature_flag_rule")
    public class FeatureFlagRuleJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "feature_flag_id", nullable = false, length = 80)
    private String featureFlagId;

    @Column(name = "rule_name", nullable = false, length = 160)
    private String ruleName;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope_type", nullable = false, length = 40)
    private ConfigurationScopeType scopeType;

    @Column(name = "scope_id", nullable = true, length = 120)
    private String scopeId;

    @Column(name = "condition_expression", nullable = true, length = 2000)
    private String conditionExpression;

    @Column(name = "percentage", nullable = true)
    private Integer percentage;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "priority_order", nullable = false)
    private int priorityOrder;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected FeatureFlagRuleJpaEntity() {
            // Required by JPA.
        }

        public FeatureFlagRuleJpaEntity(
                String id,
            String featureFlagId,
            String ruleName,
            ConfigurationScopeType scopeType,
            String scopeId,
            String conditionExpression,
            Integer percentage,
            boolean enabled,
            int priorityOrder,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.featureFlagId = featureFlagId;
        this.ruleName = ruleName;
        this.scopeType = scopeType;
        this.scopeId = scopeId;
        this.conditionExpression = conditionExpression;
        this.percentage = percentage;
        this.enabled = enabled;
        this.priorityOrder = priorityOrder;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String featureFlagId() {
        return featureFlagId;
    }


    public String ruleName() {
        return ruleName;
    }


    public ConfigurationScopeType scopeType() {
        return scopeType;
    }


    public String scopeId() {
        return scopeId;
    }


    public String conditionExpression() {
        return conditionExpression;
    }


    public Integer percentage() {
        return percentage;
    }


    public boolean enabled() {
        return enabled;
    }


    public int priorityOrder() {
        return priorityOrder;
    }


    public boolean active() {
        return active;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
