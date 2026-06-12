/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationValidationRuleJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ConfigurationValidationRule.
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
     * Database-backed JPA entity for ConfigurationValidationRule.
     */
    @Entity
    @Table(name = "hidra_configuration_validation_rule")
    public class ConfigurationValidationRuleJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "definition_id", nullable = false, length = 80)
    private String definitionId;

    @Column(name = "rule_code", nullable = false, length = 120)
    private String ruleCode;

    @Column(name = "rule_type_id", nullable = false, length = 80)
    private String ruleTypeId;

    @Column(name = "expression", nullable = true, length = 2000)
    private String expression;

    @Column(name = "configuration_json", nullable = true, columnDefinition = "jsonb")
    private String configurationJson;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ValidationRuleStatus status;

    @Column(name = "failure_message", nullable = true, length = 1000)
    private String failureMessage;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ConfigurationValidationRuleJpaEntity() {
            // Required by JPA.
        }

        public ConfigurationValidationRuleJpaEntity(
                String id,
            String definitionId,
            String ruleCode,
            String ruleTypeId,
            String expression,
            String configurationJson,
            ValidationRuleStatus status,
            String failureMessage,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.definitionId = definitionId;
        this.ruleCode = ruleCode;
        this.ruleTypeId = ruleTypeId;
        this.expression = expression;
        this.configurationJson = configurationJson;
        this.status = status;
        this.failureMessage = failureMessage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String definitionId() {
        return definitionId;
    }


    public String ruleCode() {
        return ruleCode;
    }


    public String ruleTypeId() {
        return ruleTypeId;
    }


    public String expression() {
        return expression;
    }


    public String configurationJson() {
        return configurationJson;
    }


    public ValidationRuleStatus status() {
        return status;
    }


    public String failureMessage() {
        return failureMessage;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
