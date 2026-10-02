/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentExtractionRecordJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for DocumentExtractionRecord.
 *
 */
package dz.sh.hidra.modules.documents.infrastructure.persistence.entity;

import dz.sh.hidra.modules.documents.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for DocumentExtractionRecord.
     */
    @Entity
    @Table(name = "hidra_documents_extraction_record")
    public class DocumentExtractionRecordJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "document_version_id", nullable = false, length = 80)
    private String documentVersionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "extraction_type", nullable = false, length = 40)
    private DocumentExtractionType extractionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "extraction_status", nullable = false, length = 40)
    private DocumentExtractionStatus extractionStatus;

    @Column(name = "extracted_text_ref", nullable = true, length = 500)
    private String extractedTextRef;

    @Column(name = "extracted_metadata_json", nullable = true, columnDefinition = "jsonb")
    private String extractedMetadataJson;

    @Column(name = "confidence_score", nullable = true, precision = 10, scale = 6)
    private BigDecimal confidenceScore;

    @Column(name = "language_detected", nullable = true, length = 10)
    private String languageDetected;

    @Column(name = "started_at", nullable = true)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "failure_reason", nullable = true, length = 1000)
    private String failureReason;

        protected DocumentExtractionRecordJpaEntity() {
            // Required by JPA.
        }

        public DocumentExtractionRecordJpaEntity(
                String id,
            String documentVersionId,
            DocumentExtractionType extractionType,
            DocumentExtractionStatus extractionStatus,
            String extractedTextRef,
            String extractedMetadataJson,
            BigDecimal confidenceScore,
            String languageDetected,
            Instant startedAt,
            Instant completedAt,
            String failureReason
        ) {
            this.id = id;
        this.documentVersionId = documentVersionId;
        this.extractionType = extractionType;
        this.extractionStatus = extractionStatus;
        this.extractedTextRef = extractedTextRef;
        this.extractedMetadataJson = extractedMetadataJson;
        this.confidenceScore = confidenceScore;
        this.languageDetected = languageDetected;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.failureReason = failureReason;
        }


    public String id() {
        return id;
    }


    public String documentVersionId() {
        return documentVersionId;
    }


    public DocumentExtractionType extractionType() {
        return extractionType;
    }


    public DocumentExtractionStatus extractionStatus() {
        return extractionStatus;
    }


    public String extractedTextRef() {
        return extractedTextRef;
    }


    public String extractedMetadataJson() {
        return extractedMetadataJson;
    }


    public BigDecimal confidenceScore() {
        return confidenceScore;
    }


    public String languageDetected() {
        return languageDetected;
    }


    public Instant startedAt() {
        return startedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public String failureReason() {
        return failureReason;
    }

    }
