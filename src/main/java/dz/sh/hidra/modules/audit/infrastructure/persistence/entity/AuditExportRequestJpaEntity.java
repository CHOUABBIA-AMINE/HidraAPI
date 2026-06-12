/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditExportRequestJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AuditExportRequest.
 *
 */
package dz.sh.hidra.modules.audit.infrastructure.persistence.entity;

import dz.sh.hidra.modules.audit.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for AuditExportRequest.
     */
    @Entity
    @Table(name = "hidra_audit_export_request")
    public class AuditExportRequestJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "requested_by_actor_id", nullable = false, length = 120)
    private String requestedByActorId;

    @Column(name = "requested_by_display_name_snapshot", nullable = true, length = 160)
    private String requestedByDisplayNameSnapshot;

    @Column(name = "purpose_id", nullable = false, length = 80)
    private String purposeId;

    @Column(name = "filter_json", nullable = false, columnDefinition = "jsonb")
    private String filterJson;

    @Column(name = "format", nullable = false, length = 40)
    private String format;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private AuditExportStatus status;

    @Column(name = "workflow_instance_id", nullable = true, length = 120)
    private String workflowInstanceId;

    @Column(name = "result_document_reference_id", nullable = true, length = 120)
    private String resultDocumentReferenceId;

    @Column(name = "record_count", nullable = true)
    private Integer recordCount;

    @Column(name = "checksum", nullable = true, length = 256)
    private String checksum;

    @Column(name = "requested_at", nullable = false)
    private Instant requestedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "expires_at", nullable = true)
    private Instant expiresAt;

        protected AuditExportRequestJpaEntity() {
            // Required by JPA.
        }

        public AuditExportRequestJpaEntity(
                String id,
            String requestedByActorId,
            String requestedByDisplayNameSnapshot,
            String purposeId,
            String filterJson,
            String format,
            AuditExportStatus status,
            String workflowInstanceId,
            String resultDocumentReferenceId,
            Integer recordCount,
            String checksum,
            Instant requestedAt,
            Instant completedAt,
            Instant expiresAt
        ) {
            this.id = id;
        this.requestedByActorId = requestedByActorId;
        this.requestedByDisplayNameSnapshot = requestedByDisplayNameSnapshot;
        this.purposeId = purposeId;
        this.filterJson = filterJson;
        this.format = format;
        this.status = status;
        this.workflowInstanceId = workflowInstanceId;
        this.resultDocumentReferenceId = resultDocumentReferenceId;
        this.recordCount = recordCount;
        this.checksum = checksum;
        this.requestedAt = requestedAt;
        this.completedAt = completedAt;
        this.expiresAt = expiresAt;
        }


    public String id() {
        return id;
    }


    public String requestedByActorId() {
        return requestedByActorId;
    }


    public String requestedByDisplayNameSnapshot() {
        return requestedByDisplayNameSnapshot;
    }


    public String purposeId() {
        return purposeId;
    }


    public String filterJson() {
        return filterJson;
    }


    public String format() {
        return format;
    }


    public AuditExportStatus status() {
        return status;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
    }


    public String resultDocumentReferenceId() {
        return resultDocumentReferenceId;
    }


    public Integer recordCount() {
        return recordCount;
    }


    public String checksum() {
        return checksum;
    }


    public Instant requestedAt() {
        return requestedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public Instant expiresAt() {
        return expiresAt;
    }

    }
