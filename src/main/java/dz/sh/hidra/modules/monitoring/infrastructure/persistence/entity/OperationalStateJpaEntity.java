/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalStateJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for OperationalState.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity;

import dz.sh.hidra.modules.monitoring.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for OperationalState.
     */
    @Entity
    @Table(name = "hidra_monitoring_operational_state")
    public class OperationalStateJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "topology_asset_type", nullable = false, length = 160)
    private String topologyAssetType;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code", nullable = true, length = 160)
    private String topologyAssetCode;

    @Column(name = "telemetry_point_id", nullable = true, length = 80)
    private String telemetryPointId;

    @Enumerated(EnumType.STRING)
    @Column(name = "state_value", nullable = false, length = 40)
    private OperationalStateValue stateValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = true, length = 40)
    private DeviationSeverity severity;

    @Column(name = "reason_code", nullable = true, length = 80)
    private String reasonCode;

    @Column(name = "reason_message", nullable = true, columnDefinition = "text")
    private String reasonMessage;

    @Column(name = "last_trusted_reading_id", nullable = true, length = 80)
    private String lastTrustedReadingId;

    @Column(name = "last_plan_target_id", nullable = true, length = 80)
    private String lastPlanTargetId;

    @Column(name = "state_at", nullable = false)
    private Instant stateAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected OperationalStateJpaEntity() {
            // Required by JPA.
        }

        public OperationalStateJpaEntity(
                String id,
            String topologyAssetType,
            String topologyAssetId,
            String topologyAssetCode,
            String telemetryPointId,
            OperationalStateValue stateValue,
            DeviationSeverity severity,
            String reasonCode,
            String reasonMessage,
            String lastTrustedReadingId,
            String lastPlanTargetId,
            Instant stateAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.topologyAssetType = topologyAssetType;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCode = topologyAssetCode;
        this.telemetryPointId = telemetryPointId;
        this.stateValue = stateValue;
        this.severity = severity;
        this.reasonCode = reasonCode;
        this.reasonMessage = reasonMessage;
        this.lastTrustedReadingId = lastTrustedReadingId;
        this.lastPlanTargetId = lastPlanTargetId;
        this.stateAt = stateAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String topologyAssetType() {
        return topologyAssetType;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String topologyAssetCode() {
        return topologyAssetCode;
    }


    public String telemetryPointId() {
        return telemetryPointId;
    }


    public OperationalStateValue stateValue() {
        return stateValue;
    }


    public DeviationSeverity severity() {
        return severity;
    }


    public String reasonCode() {
        return reasonCode;
    }


    public String reasonMessage() {
        return reasonMessage;
    }


    public String lastTrustedReadingId() {
        return lastTrustedReadingId;
    }


    public String lastPlanTargetId() {
        return lastPlanTargetId;
    }


    public Instant stateAt() {
        return stateAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
