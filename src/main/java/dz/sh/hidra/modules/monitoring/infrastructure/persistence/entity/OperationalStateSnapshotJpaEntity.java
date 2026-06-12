/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalStateSnapshotJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for OperationalStateSnapshot.
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
     * Database-backed JPA entity for OperationalStateSnapshot.
     */
    @Entity
    @Table(name = "hidra_monitoring_operational_state_snapshot")
    public class OperationalStateSnapshotJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "operational_state_id", nullable = false, length = 80)
    private String operationalStateId;

    @Column(name = "topology_asset_type", nullable = false, length = 160)
    private String topologyAssetType;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "telemetry_point_id", nullable = true, length = 80)
    private String telemetryPointId;

    @Enumerated(EnumType.STRING)
    @Column(name = "state_value", nullable = false, length = 40)
    private OperationalStateValue stateValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = true, length = 40)
    private DeviationSeverity severity;

    @Column(name = "snapshot_payload", nullable = true, columnDefinition = "jsonb")
    private String snapshotPayload;

    @Column(name = "captured_at", nullable = false)
    private Instant capturedAt;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

        protected OperationalStateSnapshotJpaEntity() {
            // Required by JPA.
        }

        public OperationalStateSnapshotJpaEntity(
                String id,
            String operationalStateId,
            String topologyAssetType,
            String topologyAssetId,
            String telemetryPointId,
            OperationalStateValue stateValue,
            DeviationSeverity severity,
            String snapshotPayload,
            Instant capturedAt,
            String correlationId
        ) {
            this.id = id;
        this.operationalStateId = operationalStateId;
        this.topologyAssetType = topologyAssetType;
        this.topologyAssetId = topologyAssetId;
        this.telemetryPointId = telemetryPointId;
        this.stateValue = stateValue;
        this.severity = severity;
        this.snapshotPayload = snapshotPayload;
        this.capturedAt = capturedAt;
        this.correlationId = correlationId;
        }


    public String id() {
        return id;
    }


    public String operationalStateId() {
        return operationalStateId;
    }


    public String topologyAssetType() {
        return topologyAssetType;
    }


    public String topologyAssetId() {
        return topologyAssetId;
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


    public String snapshotPayload() {
        return snapshotPayload;
    }


    public Instant capturedAt() {
        return capturedAt;
    }


    public String correlationId() {
        return correlationId;
    }

    }
