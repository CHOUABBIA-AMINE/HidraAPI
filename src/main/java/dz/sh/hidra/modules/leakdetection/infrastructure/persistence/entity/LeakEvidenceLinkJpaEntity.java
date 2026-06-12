/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakEvidenceLinkJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for LeakEvidenceLink.
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
     * Database-backed JPA entity for LeakEvidenceLink.
     */
    @Entity
    @Table(name = "hidra_leak_detection_evidence_link")
    public class LeakEvidenceLinkJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "candidate_id", nullable = false, length = 80)
    private String candidateId;

    @Enumerated(EnumType.STRING)
    @Column(name = "evidence_type", nullable = false, length = 80)
    private LeakEvidenceType evidenceType;

    @Column(name = "evidence_reference_id", nullable = false, length = 80)
    private String evidenceReferenceId;

    @Column(name = "evidence_code_snapshot", nullable = true, length = 160)
    private String evidenceCodeSnapshot;

    @Column(name = "evidence_name_snapshot", nullable = true, length = 500)
    private String evidenceNameSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "evidence_direction", nullable = false, length = 40)
    private LeakEvidenceDirection evidenceDirection;

    @Column(name = "weight", nullable = true, precision = 10, scale = 6)
    private BigDecimal weight;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected LeakEvidenceLinkJpaEntity() {
            // Required by JPA.
        }

        public LeakEvidenceLinkJpaEntity(
                String id,
            String candidateId,
            LeakEvidenceType evidenceType,
            String evidenceReferenceId,
            String evidenceCodeSnapshot,
            String evidenceNameSnapshot,
            LeakEvidenceDirection evidenceDirection,
            BigDecimal weight,
            String description,
            Instant createdAt
        ) {
            this.id = id;
        this.candidateId = candidateId;
        this.evidenceType = evidenceType;
        this.evidenceReferenceId = evidenceReferenceId;
        this.evidenceCodeSnapshot = evidenceCodeSnapshot;
        this.evidenceNameSnapshot = evidenceNameSnapshot;
        this.evidenceDirection = evidenceDirection;
        this.weight = weight;
        this.description = description;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String candidateId() {
        return candidateId;
    }


    public LeakEvidenceType evidenceType() {
        return evidenceType;
    }


    public String evidenceReferenceId() {
        return evidenceReferenceId;
    }


    public String evidenceCodeSnapshot() {
        return evidenceCodeSnapshot;
    }


    public String evidenceNameSnapshot() {
        return evidenceNameSnapshot;
    }


    public LeakEvidenceDirection evidenceDirection() {
        return evidenceDirection;
    }


    public BigDecimal weight() {
        return weight;
    }


    public String description() {
        return description;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
