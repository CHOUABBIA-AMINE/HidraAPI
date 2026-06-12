/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : KpiBandJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for KpiBand.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for KpiBand.
     */
    @Entity
    @Table(name = "hidra_analytics_kpi_band")
    public class KpiBandJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "kpi_definition_id", nullable = false, length = 80)
    private String kpiDefinitionId;

    @Column(name = "band_code", nullable = false, length = 120)
    private String bandCode;

    @Column(name = "label_ar", nullable = true, length = 160)
    private String labelAr;

    @Column(name = "label_fr", nullable = false, length = 160)
    private String labelFr;

    @Column(name = "label_en", nullable = true, length = 160)
    private String labelEn;

    @Column(name = "min_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal minValue;

    @Column(name = "max_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal maxValue;

    @Column(name = "severity_id", nullable = false, length = 80)
    private String severityId;

    @Column(name = "sort_order", nullable = false)
    private int sortOrder;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected KpiBandJpaEntity() {
            // Required by JPA.
        }

        public KpiBandJpaEntity(
                String id,
            String kpiDefinitionId,
            String bandCode,
            String labelAr,
            String labelFr,
            String labelEn,
            BigDecimal minValue,
            BigDecimal maxValue,
            String severityId,
            int sortOrder,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.kpiDefinitionId = kpiDefinitionId;
        this.bandCode = bandCode;
        this.labelAr = labelAr;
        this.labelFr = labelFr;
        this.labelEn = labelEn;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.severityId = severityId;
        this.sortOrder = sortOrder;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String kpiDefinitionId() {
        return kpiDefinitionId;
    }


    public String bandCode() {
        return bandCode;
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


    public BigDecimal minValue() {
        return minValue;
    }


    public BigDecimal maxValue() {
        return maxValue;
    }


    public String severityId() {
        return severityId;
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
