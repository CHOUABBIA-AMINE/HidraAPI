/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationRecommendationJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for SimulationRecommendation.
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
     * Database-backed JPA entity for SimulationRecommendation.
     */
    @Entity
    @Table(name = "hidra_simulation_recommendation")
    public class SimulationRecommendationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "run_id", nullable = false, length = 80)
    private String runId;

    @Column(name = "candidate_id", nullable = true, length = 80)
    private String candidateId;

    @Column(name = "recommendation_type_id", nullable = false, length = 80)
    private String recommendationTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "recommendation_status", nullable = false, length = 40)
    private SimulationRecommendationStatus recommendationStatus;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = false, length = 3000)
    private String description;

    @Column(name = "confidence_level_id", nullable = true, length = 80)
    private String confidenceLevelId;

    @Column(name = "target_module", nullable = true, length = 80)
    private String targetModule;

    @Column(name = "target_proposal_reference", nullable = true, length = 120)
    private String targetProposalReference;

    @Column(name = "published_by_actor_id", nullable = true, length = 80)
    private String publishedByActorId;

    @Column(name = "published_at", nullable = true)
    private Instant publishedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected SimulationRecommendationJpaEntity() {
            // Required by JPA.
        }

        public SimulationRecommendationJpaEntity(
                String id,
            String runId,
            String candidateId,
            String recommendationTypeId,
            SimulationRecommendationStatus recommendationStatus,
            String title,
            String description,
            String confidenceLevelId,
            String targetModule,
            String targetProposalReference,
            String publishedByActorId,
            Instant publishedAt,
            Instant createdAt
        ) {
            this.id = id;
        this.runId = runId;
        this.candidateId = candidateId;
        this.recommendationTypeId = recommendationTypeId;
        this.recommendationStatus = recommendationStatus;
        this.title = title;
        this.description = description;
        this.confidenceLevelId = confidenceLevelId;
        this.targetModule = targetModule;
        this.targetProposalReference = targetProposalReference;
        this.publishedByActorId = publishedByActorId;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String runId() {
        return runId;
    }


    public String candidateId() {
        return candidateId;
    }


    public String recommendationTypeId() {
        return recommendationTypeId;
    }


    public SimulationRecommendationStatus recommendationStatus() {
        return recommendationStatus;
    }


    public String title() {
        return title;
    }


    public String description() {
        return description;
    }


    public String confidenceLevelId() {
        return confidenceLevelId;
    }


    public String targetModule() {
        return targetModule;
    }


    public String targetProposalReference() {
        return targetProposalReference;
    }


    public String publishedByActorId() {
        return publishedByActorId;
    }


    public Instant publishedAt() {
        return publishedAt;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
