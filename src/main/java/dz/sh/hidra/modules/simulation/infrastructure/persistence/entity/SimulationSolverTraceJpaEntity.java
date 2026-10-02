/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationSolverTraceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationSolverTrace.
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
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for SimulationSolverTrace.
     */
    @Entity
    @Table(name = "hidra_simulation_solver_trace")
    public class SimulationSolverTraceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "run_id", nullable = false, length = 80)
    private String runId;

    @Column(name = "iteration_number", nullable = true)
    private Integer iterationNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "trace_level", nullable = false, length = 40)
    private SimulationTraceLevel traceLevel;

    @Column(name = "metric_code", nullable = true, length = 120)
    private String metricCode;

    @Column(name = "metric_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal metricValue;

    @Column(name = "message", nullable = true, length = 2000)
    private String message;

    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

        protected SimulationSolverTraceJpaEntity() {
            // Required by JPA.
        }

        public SimulationSolverTraceJpaEntity(
                String id,
            String runId,
            Integer iterationNumber,
            SimulationTraceLevel traceLevel,
            String metricCode,
            BigDecimal metricValue,
            String message,
            Instant recordedAt
        ) {
            this.id = id;
        this.runId = runId;
        this.iterationNumber = iterationNumber;
        this.traceLevel = traceLevel;
        this.metricCode = metricCode;
        this.metricValue = metricValue;
        this.message = message;
        this.recordedAt = recordedAt;
        }


    public String id() {
        return id;
    }


    public String runId() {
        return runId;
    }


    public Integer iterationNumber() {
        return iterationNumber;
    }


    public SimulationTraceLevel traceLevel() {
        return traceLevel;
    }


    public String metricCode() {
        return metricCode;
    }


    public BigDecimal metricValue() {
        return metricValue;
    }


    public String message() {
        return message;
    }


    public Instant recordedAt() {
        return recordedAt;
    }

    }
