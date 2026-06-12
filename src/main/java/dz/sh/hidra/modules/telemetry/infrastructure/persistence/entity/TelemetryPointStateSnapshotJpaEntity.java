/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointStateSnapshotJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for TelemetryPointStateSnapshot.
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
     * Database-backed JPA entity for TelemetryPointStateSnapshot.
     */
    @Entity
    @Table(name = "hidra_telemetry_point_state_snapshot")
    public class TelemetryPointStateSnapshotJpaEntity {

        @Id
    @Column(name = "point_id", nullable = false, length = 80)
    private String pointId;

    @Column(name = "last_reading_id", nullable = true, length = 80)
    private String lastReadingId;

    @Column(name = "last_trusted_reading_id", nullable = true, length = 80)
    private String lastTrustedReadingId;

    @Column(name = "last_numeric_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal lastNumericValue;

    @Column(name = "last_text_value", nullable = true, columnDefinition = "text")
    private String lastTextValue;

    @Column(name = "last_boolean_value", nullable = true)
    private Boolean lastBooleanValue;

    @Column(name = "last_quality_code_id", nullable = true, length = 80)
    private String lastQualityCodeId;

    @Column(name = "last_source_timestamp", nullable = true)
    private Instant lastSourceTimestamp;

    @Column(name = "last_received_at", nullable = true)
    private Instant lastReceivedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "communication_state", nullable = false, length = 40)
    private CommunicationState communicationState;

    @Column(name = "stale_since", nullable = true)
    private Instant staleSince;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected TelemetryPointStateSnapshotJpaEntity() {
            // Required by JPA.
        }

        public TelemetryPointStateSnapshotJpaEntity(
                String pointId,
            String lastReadingId,
            String lastTrustedReadingId,
            BigDecimal lastNumericValue,
            String lastTextValue,
            Boolean lastBooleanValue,
            String lastQualityCodeId,
            Instant lastSourceTimestamp,
            Instant lastReceivedAt,
            CommunicationState communicationState,
            Instant staleSince,
            Instant updatedAt
        ) {
            this.pointId = pointId;
        this.lastReadingId = lastReadingId;
        this.lastTrustedReadingId = lastTrustedReadingId;
        this.lastNumericValue = lastNumericValue;
        this.lastTextValue = lastTextValue;
        this.lastBooleanValue = lastBooleanValue;
        this.lastQualityCodeId = lastQualityCodeId;
        this.lastSourceTimestamp = lastSourceTimestamp;
        this.lastReceivedAt = lastReceivedAt;
        this.communicationState = communicationState;
        this.staleSince = staleSince;
        this.updatedAt = updatedAt;
        }


    public String pointId() {
        return pointId;
    }


    public String lastReadingId() {
        return lastReadingId;
    }


    public String lastTrustedReadingId() {
        return lastTrustedReadingId;
    }


    public BigDecimal lastNumericValue() {
        return lastNumericValue;
    }


    public String lastTextValue() {
        return lastTextValue;
    }


    public Boolean lastBooleanValue() {
        return lastBooleanValue;
    }


    public String lastQualityCodeId() {
        return lastQualityCodeId;
    }


    public Instant lastSourceTimestamp() {
        return lastSourceTimestamp;
    }


    public Instant lastReceivedAt() {
        return lastReceivedAt;
    }


    public CommunicationState communicationState() {
        return communicationState;
    }


    public Instant staleSince() {
        return staleSince;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
