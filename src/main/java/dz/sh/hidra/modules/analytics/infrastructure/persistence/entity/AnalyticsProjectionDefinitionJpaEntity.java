/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsProjectionDefinitionJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AnalyticsProjectionDefinition.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AnalyticsProjectionDefinition.
     */
    @Entity
    @Table(name = "hidra_analytics_projection_definition")
    public class AnalyticsProjectionDefinitionJpaEntity {

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

    @Column(name = "projection_type", nullable = false, length = 120)
    private String projectionType;

    @Column(name = "calculation_policy", nullable = true, length = 1000)
    private String calculationPolicy;

    @Column(name = "refresh_policy", nullable = true, length = 500)
    private String refreshPolicy;

    @Column(name = "retention_policy", nullable = true, length = 500)
    private String retentionPolicy;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected AnalyticsProjectionDefinitionJpaEntity() {
            // Required by JPA.
        }

        public AnalyticsProjectionDefinitionJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String subjectAreaId,
            String projectionType,
            String calculationPolicy,
            String refreshPolicy,
            String retentionPolicy,
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
        this.projectionType = projectionType;
        this.calculationPolicy = calculationPolicy;
        this.refreshPolicy = refreshPolicy;
        this.retentionPolicy = retentionPolicy;
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


    public String projectionType() {
        return projectionType;
    }


    public String calculationPolicy() {
        return calculationPolicy;
    }


    public String refreshPolicy() {
        return refreshPolicy;
    }


    public String retentionPolicy() {
        return retentionPolicy;
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
