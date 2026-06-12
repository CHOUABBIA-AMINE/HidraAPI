/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRunStepJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationRunStep.
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
     * Database-backed JPA entity for SimulationRunStep.
     */
    @Entity
    @Table(name = "hidra_simulation_run_step")
    public class SimulationRunStepJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "run_id", nullable = false, length = 80)
    private String runId;

    @Column(name = "step_order", nullable = false)
    private int stepOrder;

    @Column(name = "step_code", nullable = false, length = 120)
    private String stepCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private SimulationRunStepStatus status;

    @Column(name = "started_at", nullable = true)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "message", nullable = true, length = 2000)
    private String message;

        protected SimulationRunStepJpaEntity() {
            // Required by JPA.
        }

        public SimulationRunStepJpaEntity(
                String id,
            String runId,
            int stepOrder,
            String stepCode,
            SimulationRunStepStatus status,
            Instant startedAt,
            Instant completedAt,
            String message
        ) {
            this.id = id;
        this.runId = runId;
        this.stepOrder = stepOrder;
        this.stepCode = stepCode;
        this.status = status;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.message = message;
        }


    public String id() {
        return id;
    }


    public String runId() {
        return runId;
    }


    public int stepOrder() {
        return stepOrder;
    }


    public String stepCode() {
        return stepCode;
    }


    public SimulationRunStepStatus status() {
        return status;
    }


    public Instant startedAt() {
        return startedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public String message() {
        return message;
    }

    }
