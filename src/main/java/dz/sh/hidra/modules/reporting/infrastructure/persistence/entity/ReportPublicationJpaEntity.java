/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportPublicationJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ReportPublication.
 *
 */
package dz.sh.hidra.modules.reporting.infrastructure.persistence.entity;

import dz.sh.hidra.modules.reporting.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for ReportPublication.
     */
    @Entity
    @Table(name = "hidra_reporting_publication")
    public class ReportPublicationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "report_run_id", nullable = false, length = 80)
    private String reportRunId;

    @Enumerated(EnumType.STRING)
    @Column(name = "publication_status", nullable = false, length = 40)
    private ReportPublicationStatus publicationStatus;

    @Column(name = "published_by_actor_id", nullable = true, length = 80)
    private String publishedByActorId;

    @Column(name = "published_by_display_name_snapshot", nullable = true, length = 160)
    private String publishedByDisplayNameSnapshot;

    @Column(name = "published_at", nullable = true)
    private Instant publishedAt;

    @Column(name = "publication_note", nullable = true, length = 2000)
    private String publicationNote;

    @Column(name = "workflow_reference_id", nullable = true, length = 120)
    private String workflowReferenceId;

    @Column(name = "audit_reference_id", nullable = true, length = 120)
    private String auditReferenceId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ReportPublicationJpaEntity() {
            // Required by JPA.
        }

        public ReportPublicationJpaEntity(
                String id,
            String reportRunId,
            ReportPublicationStatus publicationStatus,
            String publishedByActorId,
            String publishedByDisplayNameSnapshot,
            Instant publishedAt,
            String publicationNote,
            String workflowReferenceId,
            String auditReferenceId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.reportRunId = reportRunId;
        this.publicationStatus = publicationStatus;
        this.publishedByActorId = publishedByActorId;
        this.publishedByDisplayNameSnapshot = publishedByDisplayNameSnapshot;
        this.publishedAt = publishedAt;
        this.publicationNote = publicationNote;
        this.workflowReferenceId = workflowReferenceId;
        this.auditReferenceId = auditReferenceId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String reportRunId() {
        return reportRunId;
    }


    public ReportPublicationStatus publicationStatus() {
        return publicationStatus;
    }


    public String publishedByActorId() {
        return publishedByActorId;
    }


    public String publishedByDisplayNameSnapshot() {
        return publishedByDisplayNameSnapshot;
    }


    public Instant publishedAt() {
        return publishedAt;
    }


    public String publicationNote() {
        return publicationNote;
    }


    public String workflowReferenceId() {
        return workflowReferenceId;
    }


    public String auditReferenceId() {
        return auditReferenceId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
