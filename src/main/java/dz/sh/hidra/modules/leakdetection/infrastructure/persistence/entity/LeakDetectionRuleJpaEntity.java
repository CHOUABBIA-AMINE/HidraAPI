/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionRuleJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for LeakDetectionRule.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity;

import dz.sh.hidra.modules.leakdetection.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for LeakDetectionRule.
     */
    @Entity
    @Table(name = "hidra_leak_detection_rule")
    public class LeakDetectionRuleJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "profile_id", nullable = false, length = 80)
    private String profileId;

    @Column(name = "method_id", nullable = false, length = 80)
    private String methodId;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "rule_type", nullable = false, length = 120)
    private String ruleType;

    @Column(name = "expression", nullable = true, columnDefinition = "text")
    private String expression;

    @Column(name = "parameter_json", nullable = true, columnDefinition = "jsonb")
    private String parameterJson;

    @Column(name = "threshold_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal thresholdValue;

    @Column(name = "unit_id", nullable = true, length = 80)
    private String unitId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private LeakDetectionRuleStatus status;

    @Column(name = "valid_from", nullable = true)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected LeakDetectionRuleJpaEntity() {
            // Required by JPA.
        }

        public LeakDetectionRuleJpaEntity(
                String id,
            String profileId,
            String methodId,
            String code,
            String nameFr,
            String ruleType,
            String expression,
            String parameterJson,
            BigDecimal thresholdValue,
            String unitId,
            LeakDetectionRuleStatus status,
            Instant validFrom,
            Instant validTo,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.profileId = profileId;
        this.methodId = methodId;
        this.code = code;
        this.nameFr = nameFr;
        this.ruleType = ruleType;
        this.expression = expression;
        this.parameterJson = parameterJson;
        this.thresholdValue = thresholdValue;
        this.unitId = unitId;
        this.status = status;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String profileId() {
        return profileId;
    }


    public String methodId() {
        return methodId;
    }


    public String code() {
        return code;
    }


    public String nameFr() {
        return nameFr;
    }


    public String ruleType() {
        return ruleType;
    }


    public String expression() {
        return expression;
    }


    public String parameterJson() {
        return parameterJson;
    }


    public BigDecimal thresholdValue() {
        return thresholdValue;
    }


    public String unitId() {
        return unitId;
    }


    public LeakDetectionRuleStatus status() {
        return status;
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
