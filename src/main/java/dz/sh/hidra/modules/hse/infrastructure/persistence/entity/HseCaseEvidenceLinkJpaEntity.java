/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCaseEvidenceLinkJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for HseCaseEvidenceLink.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.entity;

import dz.sh.hidra.modules.hse.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for HseCaseEvidenceLink.
     */
    @Entity
    @Table(name = "hidra_hse_case_evidence_link")
    public class HseCaseEvidenceLinkJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "hse_case_id", nullable = false, length = 80)
    private String hseCaseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "evidence_type", nullable = false, length = 80)
    private HseEvidenceType evidenceType;

    @Column(name = "evidence_reference_id", nullable = false, length = 80)
    private String evidenceReferenceId;

    @Column(name = "evidence_code_snapshot", nullable = true, length = 160)
    private String evidenceCodeSnapshot;

    @Column(name = "evidence_label_snapshot", nullable = true, length = 500)
    private String evidenceLabelSnapshot;

    @Column(name = "evidence_summary", nullable = true, columnDefinition = "text")
    private String evidenceSummary;

    @Column(name = "evidence_timestamp", nullable = true)
    private Instant evidenceTimestamp;

    @Column(name = "attached_by_actor_id", nullable = true, length = 80)
    private String attachedByActorId;

    @Column(name = "attached_at", nullable = false)
    private Instant attachedAt;

        protected HseCaseEvidenceLinkJpaEntity() {
            // Required by JPA.
        }

        public HseCaseEvidenceLinkJpaEntity(
                String id,
            String hseCaseId,
            HseEvidenceType evidenceType,
            String evidenceReferenceId,
            String evidenceCodeSnapshot,
            String evidenceLabelSnapshot,
            String evidenceSummary,
            Instant evidenceTimestamp,
            String attachedByActorId,
            Instant attachedAt
        ) {
            this.id = id;
        this.hseCaseId = hseCaseId;
        this.evidenceType = evidenceType;
        this.evidenceReferenceId = evidenceReferenceId;
        this.evidenceCodeSnapshot = evidenceCodeSnapshot;
        this.evidenceLabelSnapshot = evidenceLabelSnapshot;
        this.evidenceSummary = evidenceSummary;
        this.evidenceTimestamp = evidenceTimestamp;
        this.attachedByActorId = attachedByActorId;
        this.attachedAt = attachedAt;
        }


    public String id() {
        return id;
    }


    public String hseCaseId() {
        return hseCaseId;
    }


    public HseEvidenceType evidenceType() {
        return evidenceType;
    }


    public String evidenceReferenceId() {
        return evidenceReferenceId;
    }


    public String evidenceCodeSnapshot() {
        return evidenceCodeSnapshot;
    }


    public String evidenceLabelSnapshot() {
        return evidenceLabelSnapshot;
    }


    public String evidenceSummary() {
        return evidenceSummary;
    }


    public Instant evidenceTimestamp() {
        return evidenceTimestamp;
    }


    public String attachedByActorId() {
        return attachedByActorId;
    }


    public Instant attachedAt() {
        return attachedAt;
    }

    }
