/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportParameterDefinitionJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ReportParameterDefinition.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.entity;

import dz.sh.hidra.modules.reporting.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for ReportParameterDefinition.
     */
    @Entity
    @Table(name = "hidra_reporting_parameter_definition")
    public class ReportParameterDefinitionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "report_definition_id", nullable = false, length = 80)
    private String reportDefinitionId;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "label_ar", nullable = true, length = 160)
    private String labelAr;

    @Column(name = "label_fr", nullable = false, length = 160)
    private String labelFr;

    @Column(name = "label_en", nullable = true, length = 160)
    private String labelEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "parameter_type", nullable = false, length = 40)
    private ReportParameterType parameterType;

    @Column(name = "required", nullable = false)
    private boolean required;

    @Column(name = "default_value", nullable = true, length = 1000)
    private String defaultValue;

    @Column(name = "allowed_values_reference", nullable = true, length = 500)
    private String allowedValuesReference;

    @Column(name = "validation_expression", nullable = true, length = 1000)
    private String validationExpression;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ReportParameterDefinitionJpaEntity() {
            // Required by JPA.
        }

        public ReportParameterDefinitionJpaEntity(
                String id,
            String reportDefinitionId,
            String code,
            String labelAr,
            String labelFr,
            String labelEn,
            ReportParameterType parameterType,
            boolean required,
            String defaultValue,
            String allowedValuesReference,
            String validationExpression,
            int sortOrder,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.reportDefinitionId = reportDefinitionId;
        this.code = code;
        this.labelAr = labelAr;
        this.labelFr = labelFr;
        this.labelEn = labelEn;
        this.parameterType = parameterType;
        this.required = required;
        this.defaultValue = defaultValue;
        this.allowedValuesReference = allowedValuesReference;
        this.validationExpression = validationExpression;
        this.sortOrder = sortOrder;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String reportDefinitionId() {
        return reportDefinitionId;
    }


    public String code() {
        return code;
    }


    public String labelAr() {
        return labelAr;
    }


    public String labelFr() {
        return labelFr;
    }


    public String labelEn() {
        return labelEn;
    }


    public ReportParameterType parameterType() {
        return parameterType;
    }


    public boolean required() {
        return required;
    }


    public String defaultValue() {
        return defaultValue;
    }


    public String allowedValuesReference() {
        return allowedValuesReference;
    }


    public String validationExpression() {
        return validationExpression;
    }


    public int sortOrder() {
        return sortOrder;
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
