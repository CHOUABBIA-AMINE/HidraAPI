/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakCandidateJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for LeakCandidate.
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
     * Database-backed JPA entity for LeakCandidate.
     */
    @Entity
    @Table(name = "hidra_leak_detection_candidate")
    public class LeakCandidateJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "run_id", nullable = true, length = 80)
    private String runId;

    @Column(name = "profile_id", nullable = false, length = 80)
    private String profileId;

    @Column(name = "candidate_number", nullable = false, length = 80)
    private String candidateNumber;

    @Column(name = "topology_asset_type", nullable = false, length = 160)
    private String topologyAssetType;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code", nullable = false, length = 160)
    private String topologyAssetCode;

    @Column(name = "topology_asset_name_snapshot", nullable = true, length = 500)
    private String topologyAssetNameSnapshot;

    @Column(name = "suspected_at", nullable = false)
    private Instant suspectedAt;

    @Column(name = "first_evidence_at", nullable = true)
    private Instant firstEvidenceAt;

    @Column(name = "confidence_score", nullable = false, precision = 10, scale = 6)
    private BigDecimal confidenceScore;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity_level", nullable = false, length = 40)
    private LeakSeverityLevel severityLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private LeakCandidateStatus status;

    @Column(name = "summary", nullable = true, length = 500)
    private String summary;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected LeakCandidateJpaEntity() {
            // Required by JPA.
        }

        public LeakCandidateJpaEntity(
                String id,
            String runId,
            String profileId,
            String candidateNumber,
            String topologyAssetType,
            String topologyAssetId,
            String topologyAssetCode,
            String topologyAssetNameSnapshot,
            Instant suspectedAt,
            Instant firstEvidenceAt,
            BigDecimal confidenceScore,
            LeakSeverityLevel severityLevel,
            LeakCandidateStatus status,
            String summary,
            String correlationId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.runId = runId;
        this.profileId = profileId;
        this.candidateNumber = candidateNumber;
        this.topologyAssetType = topologyAssetType;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCode = topologyAssetCode;
        this.topologyAssetNameSnapshot = topologyAssetNameSnapshot;
        this.suspectedAt = suspectedAt;
        this.firstEvidenceAt = firstEvidenceAt;
        this.confidenceScore = confidenceScore;
        this.severityLevel = severityLevel;
        this.status = status;
        this.summary = summary;
        this.correlationId = correlationId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String runId() {
        return runId;
    }


    public String profileId() {
        return profileId;
    }


    public String candidateNumber() {
        return candidateNumber;
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


    public String topologyAssetNameSnapshot() {
        return topologyAssetNameSnapshot;
    }


    public Instant suspectedAt() {
        return suspectedAt;
    }


    public Instant firstEvidenceAt() {
        return firstEvidenceAt;
    }


    public BigDecimal confidenceScore() {
        return confidenceScore;
    }


    public LeakSeverityLevel severityLevel() {
        return severityLevel;
    }


    public LeakCandidateStatus status() {
        return status;
    }


    public String summary() {
        return summary;
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
