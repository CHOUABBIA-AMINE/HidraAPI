/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportOutputArtifactJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ReportOutputArtifact.
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
     * Database-backed JPA entity for ReportOutputArtifact.
     */
    @Entity
    @Table(name = "hidra_reporting_output_artifact")
    public class ReportOutputArtifactJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "report_run_id", nullable = false, length = 80)
    private String reportRunId;

    @Enumerated(EnumType.STRING)
    @Column(name = "artifact_type", nullable = false, length = 40)
    private ReportArtifactType artifactType;

    @Enumerated(EnumType.STRING)
    @Column(name = "format", nullable = false, length = 20)
    private ReportFormat format;

    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @Column(name = "mime_type", nullable = false, length = 120)
    private String mimeType;

    @Column(name = "storage_object_reference_id", nullable = true, length = 120)
    private String storageObjectReferenceId;

    @Column(name = "document_reference_id", nullable = true, length = 120)
    private String documentReferenceId;

    @Column(name = "checksum", nullable = false, length = 160)
    private String checksum;

    @Column(name = "size_bytes", nullable = true)
    private Long sizeBytes;

    @Column(name = "generated_at", nullable = false)
    private Instant generatedAt;

    @Column(name = "expires_at", nullable = true)
    private Instant expiresAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected ReportOutputArtifactJpaEntity() {
            // Required by JPA.
        }

        public ReportOutputArtifactJpaEntity(
                String id,
            String reportRunId,
            ReportArtifactType artifactType,
            ReportFormat format,
            String fileName,
            String mimeType,
            String storageObjectReferenceId,
            String documentReferenceId,
            String checksum,
            Long sizeBytes,
            Instant generatedAt,
            Instant expiresAt,
            Instant createdAt
        ) {
            this.id = id;
        this.reportRunId = reportRunId;
        this.artifactType = artifactType;
        this.format = format;
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.storageObjectReferenceId = storageObjectReferenceId;
        this.documentReferenceId = documentReferenceId;
        this.checksum = checksum;
        this.sizeBytes = sizeBytes;
        this.generatedAt = generatedAt;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String reportRunId() {
        return reportRunId;
    }


    public ReportArtifactType artifactType() {
        return artifactType;
    }


    public ReportFormat format() {
        return format;
    }


    public String fileName() {
        return fileName;
    }


    public String mimeType() {
        return mimeType;
    }


    public String storageObjectReferenceId() {
        return storageObjectReferenceId;
    }


    public String documentReferenceId() {
        return documentReferenceId;
    }


    public String checksum() {
        return checksum;
    }


    public Long sizeBytes() {
        return sizeBytes;
    }


    public Instant generatedAt() {
        return generatedAt;
    }


    public Instant expiresAt() {
        return expiresAt;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
