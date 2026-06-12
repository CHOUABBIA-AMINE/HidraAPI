/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationCandidateScoreJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationCandidateScore.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.persistence.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for SimulationCandidateScore.
     */
    @Entity
    @Table(name = "hidra_simulation_candidate_score")
    public class SimulationCandidateScoreJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "candidate_id", nullable = false, length = 80)
    private String candidateId;

    @Column(name = "objective_id", nullable = true, length = 80)
    private String objectiveId;

    @Column(name = "score_code", nullable = false, length = 120)
    private String scoreCode;

    @Column(name = "score_value", nullable = false, precision = 18, scale = 6)
    private BigDecimal scoreValue;

    @Column(name = "weight", nullable = true, precision = 18, scale = 6)
    private BigDecimal weight;

    @Column(name = "rank_contribution", nullable = true, precision = 18, scale = 6)
    private BigDecimal rankContribution;

    @Column(name = "explanation", nullable = true, length = 2000)
    private String explanation;

        protected SimulationCandidateScoreJpaEntity() {
            // Required by JPA.
        }

        public SimulationCandidateScoreJpaEntity(
                String id,
            String candidateId,
            String objectiveId,
            String scoreCode,
            BigDecimal scoreValue,
            BigDecimal weight,
            BigDecimal rankContribution,
            String explanation
        ) {
            this.id = id;
        this.candidateId = candidateId;
        this.objectiveId = objectiveId;
        this.scoreCode = scoreCode;
        this.scoreValue = scoreValue;
        this.weight = weight;
        this.rankContribution = rankContribution;
        this.explanation = explanation;
        }


    public String id() {
        return id;
    }


    public String candidateId() {
        return candidateId;
    }


    public String objectiveId() {
        return objectiveId;
    }


    public String scoreCode() {
        return scoreCode;
    }


    public BigDecimal scoreValue() {
        return scoreValue;
    }


    public BigDecimal weight() {
        return weight;
    }


    public BigDecimal rankContribution() {
        return rankContribution;
    }


    public String explanation() {
        return explanation;
    }

    }
