/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationResultSummaryJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationResultSummary.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for SimulationResultSummary.
     */
    @Entity
    @Table(name = "hidra_simulation_result_summary")
    public class SimulationResultSummaryJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "run_id", nullable = false, length = 80)
    private String runId;

    @Column(name = "feasible", nullable = false)
    private boolean feasible;

    @Column(name = "objective_score", nullable = true, precision = 18, scale = 6)
    private BigDecimal objectiveScore;

    @Column(name = "constraint_violation_count", nullable = false)
    private int constraintViolationCount;

    @Column(name = "warning_count", nullable = false)
    private int warningCount;

    @Column(name = "result_status_id", nullable = false, length = 80)
    private String resultStatusId;

    @Column(name = "summary_text", nullable = true, length = 2000)
    private String summaryText;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected SimulationResultSummaryJpaEntity() {
            // Required by JPA.
        }

        public SimulationResultSummaryJpaEntity(
                String id,
            String runId,
            boolean feasible,
            BigDecimal objectiveScore,
            int constraintViolationCount,
            int warningCount,
            String resultStatusId,
            String summaryText,
            Instant createdAt
        ) {
            this.id = id;
        this.runId = runId;
        this.feasible = feasible;
        this.objectiveScore = objectiveScore;
        this.constraintViolationCount = constraintViolationCount;
        this.warningCount = warningCount;
        this.resultStatusId = resultStatusId;
        this.summaryText = summaryText;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String runId() {
        return runId;
    }


    public boolean feasible() {
        return feasible;
    }


    public BigDecimal objectiveScore() {
        return objectiveScore;
    }


    public int constraintViolationCount() {
        return constraintViolationCount;
    }


    public int warningCount() {
        return warningCount;
    }


    public String resultStatusId() {
        return resultStatusId;
    }


    public String summaryText() {
        return summaryText;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
