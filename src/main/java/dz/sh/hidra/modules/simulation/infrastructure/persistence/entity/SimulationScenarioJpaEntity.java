/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationScenarioJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationScenario.
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
     * Database-backed JPA entity for SimulationScenario.
     */
    @Entity
    @Table(name = "hidra_simulation_scenario")
    public class SimulationScenarioJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "scenario_type_id", nullable = false, length = 80)
    private String scenarioTypeId;

    @Column(name = "model_id", nullable = false, length = 80)
    private String modelId;

    @Column(name = "model_version_id", nullable = false, length = 80)
    private String modelVersionId;

    @Column(name = "topology_snapshot_id", nullable = false, length = 120)
    private String topologySnapshotId;

    @Column(name = "planning_reference_id", nullable = true, length = 120)
    private String planningReferenceId;

    @Column(name = "monitoring_context_id", nullable = true, length = 120)
    private String monitoringContextId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private SimulationScenarioStatus status;

    @Column(name = "created_by_actor_id", nullable = false, length = 80)
    private String createdByActorId;

    @Column(name = "created_by_display_name_snapshot", nullable = false, length = 160)
    private String createdByDisplayNameSnapshot;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected SimulationScenarioJpaEntity() {
            // Required by JPA.
        }

        public SimulationScenarioJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String scenarioTypeId,
            String modelId,
            String modelVersionId,
            String topologySnapshotId,
            String planningReferenceId,
            String monitoringContextId,
            SimulationScenarioStatus status,
            String createdByActorId,
            String createdByDisplayNameSnapshot,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.scenarioTypeId = scenarioTypeId;
        this.modelId = modelId;
        this.modelVersionId = modelVersionId;
        this.topologySnapshotId = topologySnapshotId;
        this.planningReferenceId = planningReferenceId;
        this.monitoringContextId = monitoringContextId;
        this.status = status;
        this.createdByActorId = createdByActorId;
        this.createdByDisplayNameSnapshot = createdByDisplayNameSnapshot;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String code() {
        return code;
    }


    public String nameAr() {
        return nameAr;
    }


    public String nameFr() {
        return nameFr;
    }


    public String nameEn() {
        return nameEn;
    }


    public String scenarioTypeId() {
        return scenarioTypeId;
    }


    public String modelId() {
        return modelId;
    }


    public String modelVersionId() {
        return modelVersionId;
    }


    public String topologySnapshotId() {
        return topologySnapshotId;
    }


    public String planningReferenceId() {
        return planningReferenceId;
    }


    public String monitoringContextId() {
        return monitoringContextId;
    }


    public SimulationScenarioStatus status() {
        return status;
    }


    public String createdByActorId() {
        return createdByActorId;
    }


    public String createdByDisplayNameSnapshot() {
        return createdByDisplayNameSnapshot;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
