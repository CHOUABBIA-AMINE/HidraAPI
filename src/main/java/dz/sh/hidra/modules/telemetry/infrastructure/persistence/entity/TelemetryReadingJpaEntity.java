/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity
 *
 * @Description : JPA entity for telemetry raw readings.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * JPA entity for telemetry raw readings.
 *
 * <p>Architecture role:
 * Persistence-only representation of telemetry data. It must not be exposed to domain, application,
 * or REST layers and must not contain business behavior.
 */
@Entity
@Table(name = "hidra_telemetry_reading")
public class TelemetryReadingJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "point_id", nullable = false)
    private String pointId;

    @Column(name = "numeric_value", nullable = true)
    private BigDecimal numericValue;

    @Column(name = "text_value", nullable = true)
    private String textValue;

    @Column(name = "boolean_value", nullable = true)
    private Boolean booleanValue;

    @Column(name = "quality_code_id", nullable = false)
    private String qualityCodeId;

    @Column(name = "source_timestamp", nullable = false)
    private Instant sourceTimestamp;

    @Column(name = "received_at", nullable = false)
    private Instant receivedAt;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "ingestion_batch_id", nullable = true)
    private String ingestionBatchId;

    @Column(name = "correlation_id", nullable = true)
    private String correlationId;

    @Column(name = "rejection_reason", nullable = true)
    private String rejectionReason;

    protected TelemetryReadingJpaEntity() {
        // Required by JPA.
    }

    public TelemetryReadingJpaEntity(
            String id,            String pointId,            BigDecimal numericValue,            String textValue,            Boolean booleanValue,            String qualityCodeId,            Instant sourceTimestamp,            Instant receivedAt,            String state,            String ingestionBatchId,            String correlationId,            String rejectionReason) {
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
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public BigDecimal getNumericValue() {
        return numericValue;
    }

    public void setNumericValue(BigDecimal numericValue) {
        this.numericValue = numericValue;
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public String getQualityCodeId() {
        return qualityCodeId;
    }

    public void setQualityCodeId(String qualityCodeId) {
        this.qualityCodeId = qualityCodeId;
    }

    public Instant getSourceTimestamp() {
        return sourceTimestamp;
    }

    public void setSourceTimestamp(Instant sourceTimestamp) {
        this.sourceTimestamp = sourceTimestamp;
    }

    public Instant getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Instant receivedAt) {
        this.receivedAt = receivedAt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIngestionBatchId() {
        return ingestionBatchId;
    }

    public void setIngestionBatchId(String ingestionBatchId) {
        this.ingestionBatchId = ingestionBatchId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }
}
