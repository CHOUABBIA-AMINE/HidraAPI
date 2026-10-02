/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskSourceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RiskSource.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.entity;

import dz.sh.hidra.modules.risk.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for RiskSource.
     */
    @Entity
    @Table(name = "hidra_risk_source")
    public class RiskSourceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "risk_assessment_id", nullable = false, length = 80)
    private String riskAssessmentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_module", nullable = false, length = 80)
    private RiskSourceModule sourceModule;

    @Column(name = "source_type", nullable = false, length = 160)
    private String sourceType;

    @Column(name = "source_id", nullable = false, length = 80)
    private String sourceId;

    @Column(name = "source_code_snapshot", nullable = true, length = 160)
    private String sourceCodeSnapshot;

    @Column(name = "source_label_snapshot", nullable = true, length = 500)
    private String sourceLabelSnapshot;

    @Column(name = "source_observed_at", nullable = true)
    private Instant sourceObservedAt;

    @Column(name = "source_severity_snapshot", nullable = true, length = 160)
    private String sourceSeveritySnapshot;

    @Column(name = "source_confidence_snapshot", nullable = true, precision = 10, scale = 6)
    private BigDecimal sourceConfidenceSnapshot;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected RiskSourceJpaEntity() {
            // Required by JPA.
        }

        public RiskSourceJpaEntity(
                String id,
            String riskAssessmentId,
            RiskSourceModule sourceModule,
            String sourceType,
            String sourceId,
            String sourceCodeSnapshot,
            String sourceLabelSnapshot,
            Instant sourceObservedAt,
            String sourceSeveritySnapshot,
            BigDecimal sourceConfidenceSnapshot,
            Instant createdAt
        ) {
            this.id = id;
        this.riskAssessmentId = riskAssessmentId;
        this.sourceModule = sourceModule;
        this.sourceType = sourceType;
        this.sourceId = sourceId;
        this.sourceCodeSnapshot = sourceCodeSnapshot;
        this.sourceLabelSnapshot = sourceLabelSnapshot;
        this.sourceObservedAt = sourceObservedAt;
        this.sourceSeveritySnapshot = sourceSeveritySnapshot;
        this.sourceConfidenceSnapshot = sourceConfidenceSnapshot;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String riskAssessmentId() {
        return riskAssessmentId;
    }


    public RiskSourceModule sourceModule() {
        return sourceModule;
    }


    public String sourceType() {
        return sourceType;
    }


    public String sourceId() {
        return sourceId;
    }


    public String sourceCodeSnapshot() {
        return sourceCodeSnapshot;
    }


    public String sourceLabelSnapshot() {
        return sourceLabelSnapshot;
    }


    public Instant sourceObservedAt() {
        return sourceObservedAt;
    }


    public String sourceSeveritySnapshot() {
        return sourceSeveritySnapshot;
    }


    public BigDecimal sourceConfidenceSnapshot() {
        return sourceConfidenceSnapshot;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
