/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationModelVersionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationModelVersion.
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
     * Database-backed JPA entity for SimulationModelVersion.
     */
    @Entity
    @Table(name = "hidra_simulation_model_version")
    public class SimulationModelVersionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "model_id", nullable = false, length = 80)
    private String modelId;

    @Column(name = "version_number", nullable = false)
    private int versionNumber;

    @Column(name = "solver_profile_id", nullable = false, length = 80)
    private String solverProfileId;

    @Column(name = "model_definition_hash", nullable = false, length = 160)
    private String modelDefinitionHash;

    @Column(name = "compatible_topology_version", nullable = true, length = 120)
    private String compatibleTopologyVersion;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private SimulationModelStatus status;

    @Column(name = "activated_at", nullable = true)
    private Instant activatedAt;

    @Column(name = "retired_at", nullable = true)
    private Instant retiredAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected SimulationModelVersionJpaEntity() {
            // Required by JPA.
        }

        public SimulationModelVersionJpaEntity(
                String id,
            String modelId,
            int versionNumber,
            String solverProfileId,
            String modelDefinitionHash,
            String compatibleTopologyVersion,
            SimulationModelStatus status,
            Instant activatedAt,
            Instant retiredAt,
            Instant createdAt
        ) {
            this.id = id;
        this.modelId = modelId;
        this.versionNumber = versionNumber;
        this.solverProfileId = solverProfileId;
        this.modelDefinitionHash = modelDefinitionHash;
        this.compatibleTopologyVersion = compatibleTopologyVersion;
        this.status = status;
        this.activatedAt = activatedAt;
        this.retiredAt = retiredAt;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String modelId() {
        return modelId;
    }


    public int versionNumber() {
        return versionNumber;
    }


    public String solverProfileId() {
        return solverProfileId;
    }


    public String modelDefinitionHash() {
        return modelDefinitionHash;
    }


    public String compatibleTopologyVersion() {
        return compatibleTopologyVersion;
    }


    public SimulationModelStatus status() {
        return status;
    }


    public Instant activatedAt() {
        return activatedAt;
    }


    public Instant retiredAt() {
        return retiredAt;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
