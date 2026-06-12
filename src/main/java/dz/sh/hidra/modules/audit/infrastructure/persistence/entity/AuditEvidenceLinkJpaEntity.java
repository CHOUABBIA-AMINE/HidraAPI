/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditEvidenceLinkJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AuditEvidenceLink.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AuditEvidenceLink.
     */
    @Entity
    @Table(name = "hidra_audit_evidence_link")
    public class AuditEvidenceLinkJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "audit_event_id", nullable = false, length = 80)
    private String auditEventId;

    @Column(name = "evidence_type_id", nullable = false, length = 80)
    private String evidenceTypeId;

    @Column(name = "reference_module", nullable = true, length = 80)
    private String referenceModule;

    @Column(name = "reference_type", nullable = false, length = 120)
    private String referenceType;

    @Column(name = "reference_id", nullable = false, length = 120)
    private String referenceId;

    @Column(name = "reference_code_snapshot", nullable = true, length = 120)
    private String referenceCodeSnapshot;

    @Column(name = "reference_label_snapshot", nullable = true, length = 240)
    private String referenceLabelSnapshot;

    @Column(name = "external_uri_masked", nullable = true, length = 500)
    private String externalUriMasked;

    @Column(name = "checksum", nullable = true, length = 256)
    private String checksum;

    @Column(name = "linked_at", nullable = false)
    private Instant linkedAt;

        protected AuditEvidenceLinkJpaEntity() {
            // Required by JPA.
        }

        public AuditEvidenceLinkJpaEntity(
                String id,
            String auditEventId,
            String evidenceTypeId,
            String referenceModule,
            String referenceType,
            String referenceId,
            String referenceCodeSnapshot,
            String referenceLabelSnapshot,
            String externalUriMasked,
            String checksum,
            Instant linkedAt
        ) {
            this.id = id;
        this.auditEventId = auditEventId;
        this.evidenceTypeId = evidenceTypeId;
        this.referenceModule = referenceModule;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.referenceCodeSnapshot = referenceCodeSnapshot;
        this.referenceLabelSnapshot = referenceLabelSnapshot;
        this.externalUriMasked = externalUriMasked;
        this.checksum = checksum;
        this.linkedAt = linkedAt;
        }


    public String id() {
        return id;
    }


    public String auditEventId() {
        return auditEventId;
    }


    public String evidenceTypeId() {
        return evidenceTypeId;
    }


    public String referenceModule() {
        return referenceModule;
    }


    public String referenceType() {
        return referenceType;
    }


    public String referenceId() {
        return referenceId;
    }


    public String referenceCodeSnapshot() {
        return referenceCodeSnapshot;
    }


    public String referenceLabelSnapshot() {
        return referenceLabelSnapshot;
    }


    public String externalUriMasked() {
        return externalUriMasked;
    }


    public String checksum() {
        return checksum;
    }


    public Instant linkedAt() {
        return linkedAt;
    }

    }
