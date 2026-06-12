/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationConstraintEvaluationJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationConstraintEvaluation.
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
     * Database-backed JPA entity for SimulationConstraintEvaluation.
     */
    @Entity
    @Table(name = "hidra_simulation_constraint_evaluation")
    public class SimulationConstraintEvaluationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "run_id", nullable = false, length = 80)
    private String runId;

    @Column(name = "constraint_id", nullable = false, length = 80)
    private String constraintId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private SimulationConstraintEvaluationStatus status;

    @Column(name = "observed_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal observedValue;

    @Column(name = "limit_value", nullable = true, precision = 18, scale = 6)
    private BigDecimal limitValue;

    @Column(name = "unit_code", nullable = true, length = 40)
    private String unitCode;

    @Column(name = "severity_id", nullable = false, length = 80)
    private String severityId;

    @Column(name = "explanation", nullable = true, length = 2000)
    private String explanation;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected SimulationConstraintEvaluationJpaEntity() {
            // Required by JPA.
        }

        public SimulationConstraintEvaluationJpaEntity(
                String id,
            String runId,
            String constraintId,
            SimulationConstraintEvaluationStatus status,
            BigDecimal observedValue,
            BigDecimal limitValue,
            String unitCode,
            String severityId,
            String explanation,
            Instant createdAt
        ) {
            this.id = id;
        this.runId = runId;
        this.constraintId = constraintId;
        this.status = status;
        this.observedValue = observedValue;
        this.limitValue = limitValue;
        this.unitCode = unitCode;
        this.severityId = severityId;
        this.explanation = explanation;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String runId() {
        return runId;
    }


    public String constraintId() {
        return constraintId;
    }


    public SimulationConstraintEvaluationStatus status() {
        return status;
    }


    public BigDecimal observedValue() {
        return observedValue;
    }


    public BigDecimal limitValue() {
        return limitValue;
    }


    public String unitCode() {
        return unitCode;
    }


    public String severityId() {
        return severityId;
    }


    public String explanation() {
        return explanation;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
