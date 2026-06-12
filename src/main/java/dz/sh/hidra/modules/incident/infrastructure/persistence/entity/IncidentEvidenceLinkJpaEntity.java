/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentEvidenceLinkJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IncidentEvidenceLink.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.entity;

import dz.sh.hidra.modules.incident.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for IncidentEvidenceLink.
     */
    @Entity
    @Table(name = "hidra_incident_evidence_link")
    public class IncidentEvidenceLinkJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "incident_id", nullable = false, length = 80)
    private String incidentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "evidence_type", nullable = false, length = 80)
    private IncidentEvidenceType evidenceType;

    @Column(name = "evidence_reference_id", nullable = false, length = 80)
    private String evidenceReferenceId;

    @Column(name = "evidence_reference_code", nullable = true, length = 160)
    private String evidenceReferenceCode;

    @Column(name = "evidence_title", nullable = true, length = 255)
    private String evidenceTitle;

    @Column(name = "evidence_summary", nullable = true, columnDefinition = "text")
    private String evidenceSummary;

    @Column(name = "evidence_timestamp", nullable = true)
    private Instant evidenceTimestamp;

    @Column(name = "attached_by_actor_id", nullable = false, length = 80)
    private String attachedByActorId;

    @Column(name = "attached_at", nullable = false)
    private Instant attachedAt;

        protected IncidentEvidenceLinkJpaEntity() {
            // Required by JPA.
        }

        public IncidentEvidenceLinkJpaEntity(
                String id,
            String incidentId,
            IncidentEvidenceType evidenceType,
            String evidenceReferenceId,
            String evidenceReferenceCode,
            String evidenceTitle,
            String evidenceSummary,
            Instant evidenceTimestamp,
            String attachedByActorId,
            Instant attachedAt
        ) {
            this.id = id;
        this.incidentId = incidentId;
        this.evidenceType = evidenceType;
        this.evidenceReferenceId = evidenceReferenceId;
        this.evidenceReferenceCode = evidenceReferenceCode;
        this.evidenceTitle = evidenceTitle;
        this.evidenceSummary = evidenceSummary;
        this.evidenceTimestamp = evidenceTimestamp;
        this.attachedByActorId = attachedByActorId;
        this.attachedAt = attachedAt;
        }


    public String id() {
        return id;
    }


    public String incidentId() {
        return incidentId;
    }


    public IncidentEvidenceType evidenceType() {
        return evidenceType;
    }


    public String evidenceReferenceId() {
        return evidenceReferenceId;
    }


    public String evidenceReferenceCode() {
        return evidenceReferenceCode;
    }


    public String evidenceTitle() {
        return evidenceTitle;
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
