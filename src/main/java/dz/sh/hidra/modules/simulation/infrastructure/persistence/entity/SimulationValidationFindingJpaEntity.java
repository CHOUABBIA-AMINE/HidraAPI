/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationValidationFindingJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationValidationFinding.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for SimulationValidationFinding.
     */
    @Entity
    @Table(name = "hidra_simulation_validation_finding")
    public class SimulationValidationFindingJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "scenario_id", nullable = true, length = 80)
    private String scenarioId;

    @Column(name = "run_id", nullable = true, length = 80)
    private String runId;

    @Column(name = "finding_type_id", nullable = false, length = 80)
    private String findingTypeId;

    @Column(name = "severity_id", nullable = false, length = 80)
    private String severityId;

    @Column(name = "target_type", nullable = true, length = 80)
    private String targetType;

    @Column(name = "target_id", nullable = true, length = 120)
    private String targetId;

    @Column(name = "message", nullable = false, length = 2000)
    private String message;

    @Column(name = "resolved", nullable = false)
    private boolean resolved;

    @Column(name = "resolved_at", nullable = true)
    private Instant resolvedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected SimulationValidationFindingJpaEntity() {
            // Required by JPA.
        }

        public SimulationValidationFindingJpaEntity(
                String id,
            String scenarioId,
            String runId,
            String findingTypeId,
            String severityId,
            String targetType,
            String targetId,
            String message,
            boolean resolved,
            Instant resolvedAt,
            Instant createdAt
        ) {
            this.id = id;
        this.scenarioId = scenarioId;
        this.runId = runId;
        this.findingTypeId = findingTypeId;
        this.severityId = severityId;
        this.targetType = targetType;
        this.targetId = targetId;
        this.message = message;
        this.resolved = resolved;
        this.resolvedAt = resolvedAt;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String scenarioId() {
        return scenarioId;
    }


    public String runId() {
        return runId;
    }


    public String findingTypeId() {
        return findingTypeId;
    }


    public String severityId() {
        return severityId;
    }


    public String targetType() {
        return targetType;
    }


    public String targetId() {
        return targetId;
    }


    public String message() {
        return message;
    }


    public boolean resolved() {
        return resolved;
    }


    public Instant resolvedAt() {
        return resolvedAt;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
