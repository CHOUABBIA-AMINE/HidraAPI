/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionCaseJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for LeakDetectionCase.
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
     * Database-backed JPA entity for LeakDetectionCase.
     */
    @Entity
    @Table(name = "hidra_leak_detection_case")
    public class LeakDetectionCaseJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "case_number", nullable = false, length = 80)
    private String caseNumber;

    @Column(name = "primary_candidate_id", nullable = false, length = 80)
    private String primaryCandidateId;

    @Column(name = "topology_asset_type", nullable = false, length = 160)
    private String topologyAssetType;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code", nullable = false, length = 160)
    private String topologyAssetCode;

    @Column(name = "owning_organization_unit_id", nullable = true, length = 80)
    private String owningOrganizationUnitId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private LeakDetectionCaseStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity_level", nullable = true, length = 40)
    private LeakSeverityLevel severityLevel;

    @Column(name = "confidence_score", nullable = true, precision = 10, scale = 6)
    private BigDecimal confidenceScore;

    @Column(name = "opened_at", nullable = false)
    private Instant openedAt;

    @Column(name = "closed_at", nullable = true)
    private Instant closedAt;

    @Column(name = "opened_by_actor_id", nullable = true, length = 80)
    private String openedByActorId;

    @Column(name = "closed_by_actor_id", nullable = true, length = 80)
    private String closedByActorId;

    @Column(name = "closure_reason_id", nullable = true, length = 80)
    private String closureReasonId;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected LeakDetectionCaseJpaEntity() {
            // Required by JPA.
        }

        public LeakDetectionCaseJpaEntity(
                String id,
            String caseNumber,
            String primaryCandidateId,
            String topologyAssetType,
            String topologyAssetId,
            String topologyAssetCode,
            String owningOrganizationUnitId,
            LeakDetectionCaseStatus status,
            LeakSeverityLevel severityLevel,
            BigDecimal confidenceScore,
            Instant openedAt,
            Instant closedAt,
            String openedByActorId,
            String closedByActorId,
            String closureReasonId,
            String correlationId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.caseNumber = caseNumber;
        this.primaryCandidateId = primaryCandidateId;
        this.topologyAssetType = topologyAssetType;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCode = topologyAssetCode;
        this.owningOrganizationUnitId = owningOrganizationUnitId;
        this.status = status;
        this.severityLevel = severityLevel;
        this.confidenceScore = confidenceScore;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.openedByActorId = openedByActorId;
        this.closedByActorId = closedByActorId;
        this.closureReasonId = closureReasonId;
        this.correlationId = correlationId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String caseNumber() {
        return caseNumber;
    }


    public String primaryCandidateId() {
        return primaryCandidateId;
    }


    public String topologyAssetType() {
        return topologyAssetType;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String topologyAssetCode() {
        return topologyAssetCode;
    }


    public String owningOrganizationUnitId() {
        return owningOrganizationUnitId;
    }


    public LeakDetectionCaseStatus status() {
        return status;
    }


    public LeakSeverityLevel severityLevel() {
        return severityLevel;
    }


    public BigDecimal confidenceScore() {
        return confidenceScore;
    }


    public Instant openedAt() {
        return openedAt;
    }


    public Instant closedAt() {
        return closedAt;
    }


    public String openedByActorId() {
        return openedByActorId;
    }


    public String closedByActorId() {
        return closedByActorId;
    }


    public String closureReasonId() {
        return closureReasonId;
    }


    public String correlationId() {
        return correlationId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
