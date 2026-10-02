/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationInputSnapshotJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationInputSnapshot.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for SimulationInputSnapshot.
     */
    @Entity
    @Table(name = "hidra_simulation_input_snapshot")
    public class SimulationInputSnapshotJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "scenario_id", nullable = false, length = 80)
    private String scenarioId;

    @Column(name = "topology_snapshot_id", nullable = false, length = 120)
    private String topologySnapshotId;

    @Column(name = "telemetry_snapshot_reference", nullable = true, length = 255)
    private String telemetrySnapshotReference;

    @Column(name = "planning_snapshot_reference", nullable = true, length = 255)
    private String planningSnapshotReference;

    @Column(name = "monitoring_snapshot_reference", nullable = true, length = 255)
    private String monitoringSnapshotReference;

    @Column(name = "integrity_snapshot_reference", nullable = true, length = 255)
    private String integritySnapshotReference;

    @Column(name = "asset_availability_snapshot_reference", nullable = true, length = 255)
    private String assetAvailabilitySnapshotReference;

    @Column(name = "captured_at", nullable = false)
    private Instant capturedAt;

    @Column(name = "capture_hash", nullable = false, length = 160)
    private String captureHash;

        protected SimulationInputSnapshotJpaEntity() {
            // Required by JPA.
        }

        public SimulationInputSnapshotJpaEntity(
                String id,
            String scenarioId,
            String topologySnapshotId,
            String telemetrySnapshotReference,
            String planningSnapshotReference,
            String monitoringSnapshotReference,
            String integritySnapshotReference,
            String assetAvailabilitySnapshotReference,
            Instant capturedAt,
            String captureHash
        ) {
            this.id = id;
        this.scenarioId = scenarioId;
        this.topologySnapshotId = topologySnapshotId;
        this.telemetrySnapshotReference = telemetrySnapshotReference;
        this.planningSnapshotReference = planningSnapshotReference;
        this.monitoringSnapshotReference = monitoringSnapshotReference;
        this.integritySnapshotReference = integritySnapshotReference;
        this.assetAvailabilitySnapshotReference = assetAvailabilitySnapshotReference;
        this.capturedAt = capturedAt;
        this.captureHash = captureHash;
        }


    public String id() {
        return id;
    }


    public String scenarioId() {
        return scenarioId;
    }


    public String topologySnapshotId() {
        return topologySnapshotId;
    }


    public String telemetrySnapshotReference() {
        return telemetrySnapshotReference;
    }


    public String planningSnapshotReference() {
        return planningSnapshotReference;
    }


    public String monitoringSnapshotReference() {
        return monitoringSnapshotReference;
    }


    public String integritySnapshotReference() {
        return integritySnapshotReference;
    }


    public String assetAvailabilitySnapshotReference() {
        return assetAvailabilitySnapshotReference;
    }


    public Instant capturedAt() {
        return capturedAt;
    }


    public String captureHash() {
        return captureHash;
    }

    }
