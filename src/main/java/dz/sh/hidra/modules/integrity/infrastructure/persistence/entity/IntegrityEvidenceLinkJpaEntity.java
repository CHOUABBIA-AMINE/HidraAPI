/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityEvidenceLinkJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrityEvidenceLink.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.entity;

import dz.sh.hidra.modules.integrity.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for IntegrityEvidenceLink.
     */
    @Entity
    @Table(name = "hidra_integrity_evidence_link")
    public class IntegrityEvidenceLinkJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "target_type", nullable = false, length = 80)
    private String targetType;

    @Column(name = "target_id", nullable = false, length = 80)
    private String targetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "evidence_type", nullable = false, length = 80)
    private EvidenceType evidenceType;

    @Column(name = "evidence_reference_id", nullable = false, length = 80)
    private String evidenceReferenceId;

    @Column(name = "evidence_code_snapshot", nullable = true, length = 160)
    private String evidenceCodeSnapshot;

    @Column(name = "evidence_label_snapshot", nullable = true, length = 500)
    private String evidenceLabelSnapshot;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "evidence_timestamp", nullable = true)
    private Instant evidenceTimestamp;

    @Column(name = "attached_by_actor_id", nullable = true, length = 80)
    private String attachedByActorId;

    @Column(name = "attached_at", nullable = false)
    private Instant attachedAt;

        protected IntegrityEvidenceLinkJpaEntity() {
            // Required by JPA.
        }

        public IntegrityEvidenceLinkJpaEntity(
                String id,
            String targetType,
            String targetId,
            EvidenceType evidenceType,
            String evidenceReferenceId,
            String evidenceCodeSnapshot,
            String evidenceLabelSnapshot,
            String description,
            Instant evidenceTimestamp,
            String attachedByActorId,
            Instant attachedAt
        ) {
            this.id = id;
        this.targetType = targetType;
        this.targetId = targetId;
        this.evidenceType = evidenceType;
        this.evidenceReferenceId = evidenceReferenceId;
        this.evidenceCodeSnapshot = evidenceCodeSnapshot;
        this.evidenceLabelSnapshot = evidenceLabelSnapshot;
        this.description = description;
        this.evidenceTimestamp = evidenceTimestamp;
        this.attachedByActorId = attachedByActorId;
        this.attachedAt = attachedAt;
        }


    public String id() {
        return id;
    }


    public String targetType() {
        return targetType;
    }


    public String targetId() {
        return targetId;
    }


    public EvidenceType evidenceType() {
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


    public String description() {
        return description;
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
