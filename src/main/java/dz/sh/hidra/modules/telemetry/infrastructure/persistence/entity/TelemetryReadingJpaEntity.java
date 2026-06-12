/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for TelemetryReading.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity;

import dz.sh.hidra.modules.telemetry.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for TelemetryReading.
     */
    @Entity
    @Table(name = "hidra_telemetry_reading")
    public class TelemetryReadingJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "point_id", nullable = false, length = 80)
    private String pointId;

    @Column(name = "numeric_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal numericValue;

    @Column(name = "text_value", nullable = true, columnDefinition = "text")
    private String textValue;

    @Column(name = "boolean_value", nullable = true)
    private Boolean booleanValue;

    @Column(name = "quality_code_id", nullable = false, length = 80)
    private String qualityCodeId;

    @Column(name = "source_timestamp", nullable = false)
    private Instant sourceTimestamp;

    @Column(name = "received_at", nullable = false)
    private Instant receivedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false, length = 40)
    private ReadingState state;

    @Column(name = "ingestion_batch_id", nullable = true, length = 80)
    private String ingestionBatchId;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

    @Column(name = "rejection_reason", nullable = true, columnDefinition = "text")
    private String rejectionReason;

    @Column(name = "source_sequence_number", nullable = true, length = 160)
    private String sourceSequenceNumber;

    @Column(name = "external_tag_mapping_id", nullable = true, length = 80)
    private String externalTagMappingId;

    @Column(name = "raw_payload_hash", nullable = true, length = 160)
    private String rawPayloadHash;

    @Column(name = "created_at", nullable = true)
    private Instant createdAt;

        protected TelemetryReadingJpaEntity() {
            // Required by JPA.
        }

        public TelemetryReadingJpaEntity(
                String id,
            String pointId,
            BigDecimal numericValue,
            String textValue,
            Boolean booleanValue,
            String qualityCodeId,
            Instant sourceTimestamp,
            Instant receivedAt,
            ReadingState state,
            String ingestionBatchId,
            String correlationId,
            String rejectionReason,
            String sourceSequenceNumber,
            String externalTagMappingId,
            String rawPayloadHash,
            Instant createdAt
        ) {
            this.id = id;
        this.pointId = pointId;
        this.numericValue = numericValue;
        this.textValue = textValue;
        this.booleanValue = booleanValue;
        this.qualityCodeId = qualityCodeId;
        this.sourceTimestamp = sourceTimestamp;
        this.receivedAt = receivedAt;
        this.state = state;
        this.ingestionBatchId = ingestionBatchId;
        this.correlationId = correlationId;
        this.rejectionReason = rejectionReason;
        this.sourceSequenceNumber = sourceSequenceNumber;
        this.externalTagMappingId = externalTagMappingId;
        this.rawPayloadHash = rawPayloadHash;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String pointId() {
        return pointId;
    }


    public BigDecimal numericValue() {
        return numericValue;
    }


    public String textValue() {
        return textValue;
    }


    public Boolean booleanValue() {
        return booleanValue;
    }


    public String qualityCodeId() {
        return qualityCodeId;
    }


    public Instant sourceTimestamp() {
        return sourceTimestamp;
    }


    public Instant receivedAt() {
        return receivedAt;
    }


    public ReadingState state() {
        return state;
    }


    public String ingestionBatchId() {
        return ingestionBatchId;
    }


    public String correlationId() {
        return correlationId;
    }


    public String rejectionReason() {
        return rejectionReason;
    }


    public String sourceSequenceNumber() {
        return sourceSequenceNumber;
    }


    public String externalTagMappingId() {
        return externalTagMappingId;
    }


    public String rawPayloadHash() {
        return rawPayloadHash;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
