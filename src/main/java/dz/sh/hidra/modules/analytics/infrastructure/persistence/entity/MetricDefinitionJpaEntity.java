/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MetricDefinitionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for MetricDefinition.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for MetricDefinition.
     */
    @Entity
    @Table(name = "hidra_analytics_metric_definition")
    public class MetricDefinitionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "subject_area_id", nullable = false, length = 80)
    private String subjectAreaId;

    @Column(name = "metric_type", nullable = false, length = 120)
    private String metricType;

    @Column(name = "formula_expression", nullable = false, length = 2000)
    private String formulaExpression;

    @Column(name = "unit_id", nullable = false, length = 80)
    private String unitId;

    @Column(name = "aggregation_method", nullable = false, length = 80)
    private String aggregationMethod;

    @Column(name = "period_granularity", nullable = false, length = 80)
    private String periodGranularity;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected MetricDefinitionJpaEntity() {
            // Required by JPA.
        }

        public MetricDefinitionJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String subjectAreaId,
            String metricType,
            String formulaExpression,
            String unitId,
            String aggregationMethod,
            String periodGranularity,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.subjectAreaId = subjectAreaId;
        this.metricType = metricType;
        this.formulaExpression = formulaExpression;
        this.unitId = unitId;
        this.aggregationMethod = aggregationMethod;
        this.periodGranularity = periodGranularity;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String code() {
        return code;
    }


    public String nameAr() {
        return nameAr;
    }


    public String nameFr() {
        return nameFr;
    }


    public String nameEn() {
        return nameEn;
    }


    public String subjectAreaId() {
        return subjectAreaId;
    }


    public String metricType() {
        return metricType;
    }


    public String formulaExpression() {
        return formulaExpression;
    }


    public String unitId() {
        return unitId;
    }


    public String aggregationMethod() {
        return aggregationMethod;
    }


    public String periodGranularity() {
        return periodGranularity;
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
