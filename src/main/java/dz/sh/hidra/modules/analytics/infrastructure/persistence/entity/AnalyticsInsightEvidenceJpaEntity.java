/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsInsightEvidenceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AnalyticsInsightEvidence.
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
     * Database-backed JPA entity for AnalyticsInsightEvidence.
     */
    @Entity
    @Table(name = "hidra_analytics_insight_evidence")
    public class AnalyticsInsightEvidenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "analytics_insight_id", nullable = false, length = 80)
    private String analyticsInsightId;

    @Column(name = "evidence_type", nullable = false, length = 120)
    private String evidenceType;

    @Column(name = "source_module", nullable = false, length = 80)
    private String sourceModule;

    @Column(name = "source_object_type", nullable = false, length = 120)
    private String sourceObjectType;

    @Column(name = "source_object_id", nullable = false, length = 120)
    private String sourceObjectId;

    @Column(name = "source_label_snapshot", nullable = true, length = 240)
    private String sourceLabelSnapshot;

    @Column(name = "weight", nullable = true, precision = 10, scale = 6)
    private BigDecimal weight;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected AnalyticsInsightEvidenceJpaEntity() {
            // Required by JPA.
        }

        public AnalyticsInsightEvidenceJpaEntity(
                String id,
            String analyticsInsightId,
            String evidenceType,
            String sourceModule,
            String sourceObjectType,
            String sourceObjectId,
            String sourceLabelSnapshot,
            BigDecimal weight,
            Instant createdAt
        ) {
            this.id = id;
        this.analyticsInsightId = analyticsInsightId;
        this.evidenceType = evidenceType;
        this.sourceModule = sourceModule;
        this.sourceObjectType = sourceObjectType;
        this.sourceObjectId = sourceObjectId;
        this.sourceLabelSnapshot = sourceLabelSnapshot;
        this.weight = weight;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String analyticsInsightId() {
        return analyticsInsightId;
    }


    public String evidenceType() {
        return evidenceType;
    }


    public String sourceModule() {
        return sourceModule;
    }


    public String sourceObjectType() {
        return sourceObjectType;
    }


    public String sourceObjectId() {
        return sourceObjectId;
    }


    public String sourceLabelSnapshot() {
        return sourceLabelSnapshot;
    }


    public BigDecimal weight() {
        return weight;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
