/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationOptimizationCandidateJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationOptimizationCandidate.
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
     * Database-backed JPA entity for SimulationOptimizationCandidate.
     */
    @Entity
    @Table(name = "hidra_simulation_optimization_candidate")
    public class SimulationOptimizationCandidateJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "run_id", nullable = false, length = 80)
    private String runId;

    @Column(name = "candidate_number", nullable = false)
    private int candidateNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "candidate_status", nullable = false, length = 40)
    private SimulationCandidateStatus candidateStatus;

    @Column(name = "feasible", nullable = false)
    private boolean feasible;

    @Column(name = "objective_score", nullable = true, precision = 18, scale = 6)
    private BigDecimal objectiveScore;

    @Column(name = "rank", nullable = true)
    private Integer rank;

    @Column(name = "summary_text", nullable = true, length = 2000)
    private String summaryText;

    @Column(name = "selected_by_actor_id", nullable = true, length = 80)
    private String selectedByActorId;

    @Column(name = "selected_at", nullable = true)
    private Instant selectedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected SimulationOptimizationCandidateJpaEntity() {
            // Required by JPA.
        }

        public SimulationOptimizationCandidateJpaEntity(
                String id,
            String runId,
            int candidateNumber,
            SimulationCandidateStatus candidateStatus,
            boolean feasible,
            BigDecimal objectiveScore,
            Integer rank,
            String summaryText,
            String selectedByActorId,
            Instant selectedAt,
            Instant createdAt
        ) {
            this.id = id;
        this.runId = runId;
        this.candidateNumber = candidateNumber;
        this.candidateStatus = candidateStatus;
        this.feasible = feasible;
        this.objectiveScore = objectiveScore;
        this.rank = rank;
        this.summaryText = summaryText;
        this.selectedByActorId = selectedByActorId;
        this.selectedAt = selectedAt;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String runId() {
        return runId;
    }


    public int candidateNumber() {
        return candidateNumber;
    }


    public SimulationCandidateStatus candidateStatus() {
        return candidateStatus;
    }


    public boolean feasible() {
        return feasible;
    }


    public BigDecimal objectiveScore() {
        return objectiveScore;
    }


    public Integer rank() {
        return rank;
    }


    public String summaryText() {
        return summaryText;
    }


    public String selectedByActorId() {
        return selectedByActorId;
    }


    public Instant selectedAt() {
        return selectedAt;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
