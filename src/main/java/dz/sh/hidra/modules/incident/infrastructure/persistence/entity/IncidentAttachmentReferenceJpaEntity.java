/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentAttachmentReferenceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IncidentAttachmentReference.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for IncidentAttachmentReference.
     */
    @Entity
    @Table(name = "hidra_incident_attachment_reference")
    public class IncidentAttachmentReferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "incident_id", nullable = false, length = 80)
    private String incidentId;

    @Column(name = "document_reference_id", nullable = false, length = 80)
    private String documentReferenceId;

    @Column(name = "document_type_id", nullable = true, length = 80)
    private String documentTypeId;

    @Column(name = "filename_snapshot", nullable = true, length = 255)
    private String filenameSnapshot;

    @Column(name = "content_type", nullable = true, length = 160)
    private String contentType;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "uploaded_by_actor_id", nullable = false, length = 80)
    private String uploadedByActorId;

    @Column(name = "uploaded_at", nullable = false)
    private Instant uploadedAt;

        protected IncidentAttachmentReferenceJpaEntity() {
            // Required by JPA.
        }

        public IncidentAttachmentReferenceJpaEntity(
                String id,
            String incidentId,
            String documentReferenceId,
            String documentTypeId,
            String filenameSnapshot,
            String contentType,
            String description,
            String uploadedByActorId,
            Instant uploadedAt
        ) {
            this.id = id;
        this.incidentId = incidentId;
        this.documentReferenceId = documentReferenceId;
        this.documentTypeId = documentTypeId;
        this.filenameSnapshot = filenameSnapshot;
        this.contentType = contentType;
        this.description = description;
        this.uploadedByActorId = uploadedByActorId;
        this.uploadedAt = uploadedAt;
        }


    public String id() {
        return id;
    }


    public String incidentId() {
        return incidentId;
    }


    public String documentReferenceId() {
        return documentReferenceId;
    }


    public String documentTypeId() {
        return documentTypeId;
    }


    public String filenameSnapshot() {
        return filenameSnapshot;
    }


    public String contentType() {
        return contentType;
    }


    public String description() {
        return description;
    }


    public String uploadedByActorId() {
        return uploadedByActorId;
    }


    public Instant uploadedAt() {
        return uploadedAt;
    }

    }
