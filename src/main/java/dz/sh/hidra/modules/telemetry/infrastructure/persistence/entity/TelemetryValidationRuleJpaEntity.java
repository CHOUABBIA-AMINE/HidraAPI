/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryValidationRuleJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for TelemetryValidationRule.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity;

import dz.sh.hidra.modules.telemetry.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for TelemetryValidationRule.
     */
    @Entity
    @Table(name = "hidra_telemetry_validation_rule")
    public class TelemetryValidationRuleJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "name", nullable = false, length = 160)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope_type", nullable = false, length = 80)
    private ValidationScopeType scopeType;

    @Column(name = "scope_reference_id", nullable = true, length = 80)
    private String scopeReferenceId;

    @Column(name = "rule_type_id", nullable = false, length = 80)
    private String ruleTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false, length = 40)
    private ValidationSeverity severity;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_on_failure", nullable = false, length = 40)
    private ValidationFailureAction actionOnFailure;

    @Column(name = "expression", nullable = true, columnDefinition = "text")
    private String expression;

    @Column(name = "configuration_json", nullable = true, columnDefinition = "jsonb")
    private String configurationJson;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected TelemetryValidationRuleJpaEntity() {
            // Required by JPA.
        }

        public TelemetryValidationRuleJpaEntity(
                String id,
            String code,
            String name,
            ValidationScopeType scopeType,
            String scopeReferenceId,
            String ruleTypeId,
            ValidationSeverity severity,
            ValidationFailureAction actionOnFailure,
            String expression,
            String configurationJson,
            boolean active,
            Instant validFrom,
            Instant validTo,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.name = name;
        this.scopeType = scopeType;
        this.scopeReferenceId = scopeReferenceId;
        this.ruleTypeId = ruleTypeId;
        this.severity = severity;
        this.actionOnFailure = actionOnFailure;
        this.expression = expression;
        this.configurationJson = configurationJson;
        this.active = active;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String code() {
        return code;
    }


    public String name() {
        return name;
    }


    public ValidationScopeType scopeType() {
        return scopeType;
    }


    public String scopeReferenceId() {
        return scopeReferenceId;
    }


    public String ruleTypeId() {
        return ruleTypeId;
    }


    public ValidationSeverity severity() {
        return severity;
    }


    public ValidationFailureAction actionOnFailure() {
        return actionOnFailure;
    }


    public String expression() {
        return expression;
    }


    public String configurationJson() {
        return configurationJson;
    }


    public boolean active() {
        return active;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
