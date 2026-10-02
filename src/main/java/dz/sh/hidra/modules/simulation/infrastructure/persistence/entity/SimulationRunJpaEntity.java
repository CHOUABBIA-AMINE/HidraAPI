/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRunJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationRun.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.entity;

import dz.sh.hidra.modules.simulation.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for SimulationRun.
     */
    @Entity
    @Table(name = "hidra_simulation_run")
    public class SimulationRunJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "scenario_id", nullable = false, length = 80)
    private String scenarioId;

    @Column(name = "model_version_id", nullable = false, length = 80)
    private String modelVersionId;

    @Column(name = "input_snapshot_id", nullable = false, length = 80)
    private String inputSnapshotId;

    @Column(name = "run_type_id", nullable = false, length = 80)
    private String runTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private SimulationRunStatus status;

    @Column(name = "requested_by_actor_id", nullable = false, length = 80)
    private String requestedByActorId;

    @Column(name = "requested_by_display_name_snapshot", nullable = false, length = 160)
    private String requestedByDisplayNameSnapshot;

    @Column(name = "queued_at", nullable = false)
    private Instant queuedAt;

    @Column(name = "started_at", nullable = true)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "duration_millis", nullable = true)
    private Long durationMillis;

    @Column(name = "solver_profile_id", nullable = false, length = 80)
    private String solverProfileId;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

    @Column(name = "failure_reason", nullable = true, length = 2000)
    private String failureReason;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected SimulationRunJpaEntity() {
            // Required by JPA.
        }

        public SimulationRunJpaEntity(
                String id,
            String scenarioId,
            String modelVersionId,
            String inputSnapshotId,
            String runTypeId,
            SimulationRunStatus status,
            String requestedByActorId,
            String requestedByDisplayNameSnapshot,
            Instant queuedAt,
            Instant startedAt,
            Instant completedAt,
            Long durationMillis,
            String solverProfileId,
            String correlationId,
            String failureReason,
            Instant createdAt
        ) {
            this.id = id;
        this.scenarioId = scenarioId;
        this.modelVersionId = modelVersionId;
        this.inputSnapshotId = inputSnapshotId;
        this.runTypeId = runTypeId;
        this.status = status;
        this.requestedByActorId = requestedByActorId;
        this.requestedByDisplayNameSnapshot = requestedByDisplayNameSnapshot;
        this.queuedAt = queuedAt;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.durationMillis = durationMillis;
        this.solverProfileId = solverProfileId;
        this.correlationId = correlationId;
        this.failureReason = failureReason;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String scenarioId() {
        return scenarioId;
    }


    public String modelVersionId() {
        return modelVersionId;
    }


    public String inputSnapshotId() {
        return inputSnapshotId;
    }


    public String runTypeId() {
        return runTypeId;
    }


    public SimulationRunStatus status() {
        return status;
    }


    public String requestedByActorId() {
        return requestedByActorId;
    }


    public String requestedByDisplayNameSnapshot() {
        return requestedByDisplayNameSnapshot;
    }


    public Instant queuedAt() {
        return queuedAt;
    }


    public Instant startedAt() {
        return startedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public Long durationMillis() {
        return durationMillis;
    }


    public String solverProfileId() {
        return solverProfileId;
    }


    public String correlationId() {
        return correlationId;
    }


    public String failureReason() {
        return failureReason;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
