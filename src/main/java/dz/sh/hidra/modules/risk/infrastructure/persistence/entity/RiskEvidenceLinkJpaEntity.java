/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskEvidenceLinkJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RiskEvidenceLink.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for RiskEvidenceLink.
     */
    @Entity
    @Table(name = "hidra_risk_evidence_link")
    public class RiskEvidenceLinkJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "risk_assessment_id", nullable = false, length = 80)
    private String riskAssessmentId;

    @Column(name = "evidence_module", nullable = false, length = 80)
    private String evidenceModule;

    @Column(name = "evidence_type", nullable = false, length = 160)
    private String evidenceType;

    @Column(name = "evidence_id", nullable = false, length = 80)
    private String evidenceId;

    @Column(name = "evidence_code_snapshot", nullable = true, length = 160)
    private String evidenceCodeSnapshot;

    @Column(name = "evidence_label_snapshot", nullable = true, length = 500)
    private String evidenceLabelSnapshot;

    @Column(name = "evidence_timestamp", nullable = true)
    private Instant evidenceTimestamp;

    @Column(name = "evidence_hash", nullable = true, length = 160)
    private String evidenceHash;

    @Column(name = "evidence_summary", nullable = true, columnDefinition = "text")
    private String evidenceSummary;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected RiskEvidenceLinkJpaEntity() {
            // Required by JPA.
        }

        public RiskEvidenceLinkJpaEntity(
                String id,
            String riskAssessmentId,
            String evidenceModule,
            String evidenceType,
            String evidenceId,
            String evidenceCodeSnapshot,
            String evidenceLabelSnapshot,
            Instant evidenceTimestamp,
            String evidenceHash,
            String evidenceSummary,
            Instant createdAt
        ) {
            this.id = id;
        this.riskAssessmentId = riskAssessmentId;
        this.evidenceModule = evidenceModule;
        this.evidenceType = evidenceType;
        this.evidenceId = evidenceId;
        this.evidenceCodeSnapshot = evidenceCodeSnapshot;
        this.evidenceLabelSnapshot = evidenceLabelSnapshot;
        this.evidenceTimestamp = evidenceTimestamp;
        this.evidenceHash = evidenceHash;
        this.evidenceSummary = evidenceSummary;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String riskAssessmentId() {
        return riskAssessmentId;
    }


    public String evidenceModule() {
        return evidenceModule;
    }


    public String evidenceType() {
        return evidenceType;
    }


    public String evidenceId() {
        return evidenceId;
    }


    public String evidenceCodeSnapshot() {
        return evidenceCodeSnapshot;
    }


    public String evidenceLabelSnapshot() {
        return evidenceLabelSnapshot;
    }


    public Instant evidenceTimestamp() {
        return evidenceTimestamp;
    }


    public String evidenceHash() {
        return evidenceHash;
    }


    public String evidenceSummary() {
        return evidenceSummary;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
