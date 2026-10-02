/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : KpiDefinitionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for KpiDefinition.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for KpiDefinition.
     */
    @Entity
    @Table(name = "hidra_analytics_kpi_definition")
    public class KpiDefinitionJpaEntity {

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

    @Column(name = "primary_metric_definition_id", nullable = false, length = 80)
    private String primaryMetricDefinitionId;

    @Column(name = "kpi_category_id", nullable = false, length = 80)
    private String kpiCategoryId;

    @Column(name = "display_unit_id", nullable = true, length = 80)
    private String displayUnitId;

    @Column(name = "default_granularity", nullable = false, length = 80)
    private String defaultGranularity;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected KpiDefinitionJpaEntity() {
            // Required by JPA.
        }

        public KpiDefinitionJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String subjectAreaId,
            String primaryMetricDefinitionId,
            String kpiCategoryId,
            String displayUnitId,
            String defaultGranularity,
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
        this.primaryMetricDefinitionId = primaryMetricDefinitionId;
        this.kpiCategoryId = kpiCategoryId;
        this.displayUnitId = displayUnitId;
        this.defaultGranularity = defaultGranularity;
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


    public String primaryMetricDefinitionId() {
        return primaryMetricDefinitionId;
    }


    public String kpiCategoryId() {
        return kpiCategoryId;
    }


    public String displayUnitId() {
        return displayUnitId;
    }


    public String defaultGranularity() {
        return defaultGranularity;
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
